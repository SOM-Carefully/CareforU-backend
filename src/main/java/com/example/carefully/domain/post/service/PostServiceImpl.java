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
        if (postRole == PostRole.NOTICE) {
            return false;
        }

        Category category = findCategoryById(categoryId);
        if (!category.isAssociatedToRank()) {
            return false;
        }

        if (userRole.isPaidRole() && category.isClassic()) {
            return true;
        }
        return !category.isSameRankWithUser(userRole);
    }

    @Override
    @Transactional
    public PostDto.UpdateResponse updatePost(PostDto.UpdateRequest request, Long postId) {
        Post post = findPostByIdAndUser(postId);
        post.updatePost(request.getTitle(), request.getTitle(), request.getImgUrl());
        return new PostDto.UpdateResponse(post.getId());
    }

    @Override
    @Transactional
    public void findPostAndDelete(Long postId) {
        Post post = findPostByIdAndUser(postId);
        s3Service.deleteFile(post.getImgUrl());
        postRepository.delete(post);
    }

    @Override
    public PostDto.SearchResponse searchPostDetail(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(PostEmptyException::new);
        return PostDto.SearchResponse.create(post);
    }

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
        if (postRole != PostRole.FREE) {
            return false;
        }

        Category category = findCategoryById(categoryId);
        if (!category.isAssociatedToRank()) {
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
