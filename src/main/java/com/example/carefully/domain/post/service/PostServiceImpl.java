package com.example.carefully.domain.post.service;

import com.example.carefully.domain.category.domain.Category;
import com.example.carefully.domain.category.exception.CategoryEmptyException;
import com.example.carefully.domain.category.repository.CategoryRepository;
import com.example.carefully.domain.post.domain.Post;
import com.example.carefully.domain.post.domain.PostRole;
import com.example.carefully.domain.post.dto.PostDto;
import com.example.carefully.domain.post.exception.NotValidateAccessException;
import com.example.carefully.domain.post.exception.NotValidateWriteException;
import com.example.carefully.domain.post.exception.PostEmptyException;
import com.example.carefully.domain.post.repository.CustomPostRepository;
import com.example.carefully.domain.post.repository.PostRepository;
import com.example.carefully.domain.user.entity.Role;
import com.example.carefully.domain.user.repository.UserRepository;
import com.example.carefully.global.dto.SliceDto;
import com.example.carefully.infra.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.carefully.global.utils.UserUtils.getCurrentUser;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final S3Service s3Service;
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final CustomPostRepository customPostRepository;
    private final UserRepository userRepository;

    /**
     * 작성 권한이 존재한다면 게시글을 해당 게시판 카테고리와 연결 후에 저장한다.
     *
     * @param request 게시글 제목, 내용, 이미지 경로가 담긴 객체
     * @param postRole 게시판 종류  EX) 공지 게시판 / 자유 게시판
     * @param categoryId 게시판 카테고리  EX) 1등급 / 비밀 / 만남 / 토론
     * @return 생성된 게시글의 ID
     * @throws NotValidateWriteException 글을 작성할 수 있는 권한이 없는 경우
     */
    @Override
    @Transactional
    public PostDto.CreateResponse createNewPost(PostDto.CreateRequest request,
                                                PostRole postRole,
                                                Long categoryId) {
        if (isRankFailToCreatePost(postRole, categoryId)) {
            throw new NotValidateWriteException();
        }

        Category category = null;
        if (postRole == PostRole.FREE) {
            category = findCategoryById(categoryId);
        }

        Post post = request.toEntity(postRole, category, getCurrentUser(userRepository));
        Post persistPost = postRepository.save(post);
        return new PostDto.CreateResponse(persistPost.getId());
    }

    private boolean isRankFailToCreatePost(PostRole postRole, Long categoryId) {
        Role userRole = getCurrentUser(userRepository).getRole();
        if (postRole == PostRole.NOTICE) {     // 공지 게시판인 경우 작성 허용
            return false;
        }

        Category category = findCategoryById(categoryId);
        if (isNoNeedToCheckRankValidation(category)) {    // 등급에 관련된 카테고리인 경우 작성 허용
            return false;
        }

        if (userRole.isPaidRole() && category.isClassic()) {   // 유료 등급 회원은 가장 낮은 등급의 게시판 작성 허용
            return false;
        }

        return !category.isSameRankWithUser(userRole);   // 각 회원 등급이랑 같은 등급의 게시판에만 작성 허용
    }

    private boolean isNoNeedToCheckRankValidation(Category category) {
        return !category.isAssociatedToRank();
    }

    /**
     * 게시글 제목과 내용을 수정한다.
     *
     * @param request 수정하려는 게시글 제목 / 내용 / 이미지 경로
     * @param postId 수정하려는 게시글 ID
     * @return 수정이 완료된 게시글 ID
     */
    @Override
    @Transactional
    public PostDto.UpdateResponse updatePost(PostDto.UpdateRequest request, Long postId) {
        Post post = findPostByIdAndUser(postId);
        post.updatePost(request.getTitle(), request.getTitle(), request.getImgUrl());
        return new PostDto.UpdateResponse(post.getId());
    }

    /**
     * DB에서 해당 게시글을 삭제하고 S3 버킷에서 이미지를 삭제한다.
     *
     * @param postId 삭제하려는 게시글 ID
     */
    @Override
    @Transactional
    public void findPostAndDelete(Long postId) {
        Post post = findPostByIdAndUser(postId);
        s3Service.deleteFile(post.getImgUrl());
        postRepository.delete(post);
    }

    /**
     * 게시글 하나의 정보를 반환한다.
     *
     * @param postId 조회하려는 게시글 ID
     * @return 게시글 ID / 제목 / 내용 / 작성자 / 이미지 경로 / 작성 날짜 반환
     */
    @Override
    public PostDto.SearchResponse searchPostDetail(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(PostEmptyException::new);
        return PostDto.SearchResponse.create(post);
    }

    /**
     * 조회 권한이 있는 경우 해당 카테고리에 맞는 게시판의 글 리스트를 반환한다.
     *
     * @param postRole  게시판 종류  EX) 공지 게시판 / 자유 게시판
     * @param categoryId 조회하려는 게시판 카테고리 ID  EX) 2등급 게시판 /
     * @param pageable  한 페이지에 조회할 게시글 개수
     * @return  게시글 ID / 제목 / 내용 / 작성자 / 이미지 경로 / 작성 날짜를 페이지 정보와 함께 SliceDto 형태로 반환
ㅇ    * @throws NotValidateAccessException 글을 조회할 수 있는 권한이 없는 경우
     */
    @Override
    public SliceDto<PostDto.SearchResponse> searchPostList(String postRole,
                                                           Long categoryId,
                                                           Pageable pageable) {
        PostRole role = PostRole.valueOf(postRole);
        if (isRankFailToAccessPost(role, categoryId)) {
            throw new NotValidateAccessException();
        }

        Slice<PostDto.SearchResponse> postsByRole = customPostRepository
                .getPostList(pageable, role, categoryId)
                .map(PostDto.SearchResponse::create);

        return SliceDto.create(postsByRole);
    }

    private boolean isRankFailToAccessPost(PostRole postRole, Long categoryId) {
        Role userRole = getCurrentUser(userRepository).getRole();
        if (postRole == PostRole.NOTICE) {
            return false;
        }

        Category category = findCategoryById(categoryId);
        if (isNoNeedToCheckRankValidation(category)) {
            return false;
        }

        Role postRank = Role.of(category.getName());
        return !postRank.canAccess(userRole.getRank());
    }

    private Category findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(CategoryEmptyException::new);
    }

    private Post findPostByIdAndUser(Long postId) {
        return postRepository.findByIdAndUser(postId, getCurrentUser(userRepository))
                .orElseThrow(PostEmptyException::new);
    }
}
