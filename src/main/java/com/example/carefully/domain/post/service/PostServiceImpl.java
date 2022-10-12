package com.example.carefully.domain.post.service;

import com.example.carefully.domain.post.domain.Post;
import com.example.carefully.domain.post.domain.PostRole;
import com.example.carefully.domain.post.dto.PostDto;
import com.example.carefully.domain.post.exception.PostEmptyException;
import com.example.carefully.domain.post.repository.PostRepository;
import com.example.carefully.global.dto.SliceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final Long tempUserId = 1L;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    @Transactional
    public PostDto.CreateResponse createNewPost(PostDto.CreateRequest request, String postRole) {
        Post savedPost = postRepository.save(request.toEntity(PostRole.valueOf(postRole), tempUserId));
        return new PostDto.CreateResponse(savedPost.getId());
    }

    @Transactional
    public PostDto.UpdateResponse updatePost(PostDto.UpdateRequest request, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(PostEmptyException::new);
        post.update(request.getTitle(), request.getTitle());
        return new PostDto.UpdateResponse(post.getId());
    }

    @Transactional
    public void findPostAndDelete(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(PostEmptyException::new);
        postRepository.delete(post);
    }

    public PostDto.SearchResponse searchPostDetail(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(PostEmptyException::new);
        return searchResponseBuilder(post);
    }

    public SliceDto<PostDto.SearchResponse> searchPostList(String postRole, Pageable pageable) {
        Slice<PostDto.SearchResponse> pageDtoList = postRepository.findAllByPostRoleOrderByCreatedAtDesc(
                pageable, PostRole.valueOf(postRole)).map(this::searchResponseBuilder);
        return SliceDto.create(pageDtoList);
    }

    private PostDto.SearchResponse searchResponseBuilder(Post post){
        return PostDto.SearchResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .writer(post.getUserId().toString())
                .createdAt(post.getCreatedAt().format(formatter)).build();
    }
}
