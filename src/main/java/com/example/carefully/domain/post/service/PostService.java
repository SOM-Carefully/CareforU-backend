package com.example.carefully.domain.post.service;

import com.example.carefully.domain.post.domain.PostRole;
import com.example.carefully.domain.post.dto.PostDto;
import com.example.carefully.global.dto.SliceDto;
import org.springframework.data.domain.Pageable;

public interface PostService {
    PostDto.CreateResponse createNewPost(PostDto.CreateRequest request, PostRole postRole, Long categoryId);
    PostDto.UpdateResponse updatePost(PostDto.UpdateRequest request, Long postId);
    PostDto.SearchResponse searchPostDetail(Long postId);
    SliceDto<PostDto.SearchResponse> searchPostList(String postRole, Long categoryId, Pageable pageable);
    void findPostAndDelete(Long postId);
}
