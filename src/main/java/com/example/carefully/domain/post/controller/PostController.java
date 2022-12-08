package com.example.carefully.domain.post.controller;

import com.example.carefully.domain.post.service.PostServiceImpl;
import com.example.carefully.global.dto.SliceDto;
import com.example.carefully.global.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<BaseResponse<CreateResponse>> createPost(@RequestParam("role") String postRole,
                                                                   @RequestParam(value = "category", required = false) Long categoryId,
                                                                   @RequestBody CreateRequest createRequest) {
        return ResponseEntity.ok(BaseResponse.create(
                CREATE_POST_SUCCESS.getMessage(), postService.createNewPost(createRequest, postRole, categoryId)));
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<BaseResponse<UpdateResponse>> updatePost(@PathVariable("postId") Long postId,
                                                                   @RequestBody UpdateRequest updateRequest) {
        return ResponseEntity.ok(BaseResponse.create(
                UPDATE_POST_SUCCESS.getMessage(), postService.updatePost(updateRequest, postId)));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<BaseResponse<SearchResponse>> searchPostDetail(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok(BaseResponse.create(
                GET_POST_DETAIL_SUCCESS.getMessage(), postService.searchPostDetail(postId)));
    }

    @GetMapping
    public ResponseEntity<BaseResponse<SliceDto<SearchResponse>>> searchPostList(@RequestParam("role") String postRole,
                                                                                 @RequestParam(value = "category", required = false) Long categoryId,
                                                                                 @PageableDefault(size = 100) Pageable pageable) {
        return ResponseEntity.ok(BaseResponse.create(
                GET_POST_LIST_SUCCESS.getMessage(), postService.searchPostList(postRole, categoryId, pageable)));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<BaseResponse<String>> deletePost(@PathVariable("postId") Long postId) {
        postService.findPostAndDelete(postId);
        return ResponseEntity.ok(BaseResponse.create(DELETE_POST_SUCCESS.getMessage()));
    }
}
