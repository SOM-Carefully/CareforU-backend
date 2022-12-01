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

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final Long tempUserId = 1L;

    @Override
    @Transactional
    public PostDto.CreateResponse createNewPost(PostDto.CreateRequest request, String postRole) {
        Post savedPost = postRepository.save(request.toEntity(PostRole.valueOf(postRole), tempUserId));
        return new PostDto.CreateResponse(savedPost.getId());
    }

    @Override
    @Transactional
    public PostDto.UpdateResponse updatePost(PostDto.UpdateRequest request, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(PostEmptyException::new);
        post.update(request.getTitle(), request.getTitle(), request.getImgUrl());
        return new PostDto.UpdateResponse(post.getId());
    }

    @Override
    @Transactional
    public void findPostAndDelete(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(PostEmptyException::new);
        postRepository.delete(post);
    }

    @Override
    public PostDto.SearchResponse searchPostDetail(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(PostEmptyException::new);
        return PostDto.SearchResponse.create(post);
    }

    @Override
    public SliceDto<PostDto.SearchResponse> searchPostList(String postRole, Pageable pageable) {
        Slice<PostDto.SearchResponse> pageDtoList = postRepository.findAllByPostRoleOrderByCreatedAtDesc(
                pageable, PostRole.valueOf(postRole)).map(PostDto.SearchResponse::create);
        return SliceDto.create(pageDtoList);
    }
}
