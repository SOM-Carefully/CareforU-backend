package com.example.carefully.domain.post.controller;

import com.example.carefully.domain.post.dto.PostResponseMessage;
import com.example.carefully.domain.post.service.PostServiceImpl;
import com.example.carefully.global.error.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static com.example.carefully.domain.post.dto.PostDto.*;
import static com.example.carefully.domain.post.dto.PostResponseMessage.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostServiceImpl postService;

    @PostMapping
    public ResponseEntity<BaseResponse<CreateResponse>> createPost(@RequestParam("category") String postRole, @RequestBody CreateRequest createRequest){
        return ResponseEntity.ok(BaseResponse.create(CREATE_POST_SUCCESS.getMessage(), postService.createNewPost(createRequest, postRole)));
    }
}
