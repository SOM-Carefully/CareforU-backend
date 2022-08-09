package com.example.carefully.domain.post.service;

import com.example.carefully.domain.post.dto.PostDto;

public interface PostService {
    PostDto.CreateResponse createNewPost(PostDto.CreateRequest request, String postRole);
}
