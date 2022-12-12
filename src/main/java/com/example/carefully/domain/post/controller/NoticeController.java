package com.example.carefully.domain.post.controller;

import com.example.carefully.domain.post.domain.PostRole;
import com.example.carefully.domain.post.dto.PostDto;
import com.example.carefully.domain.post.service.PostServiceImpl;
import com.example.carefully.global.dto.BaseResponse;
import com.example.carefully.global.dto.SliceDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.example.carefully.domain.post.dto.PostResponseMessage.*;

@RestController
@RequestMapping("/api/v1/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final PostServiceImpl postService;

    @ApiOperation(value = "공지게시판 글 등록", notes = "관리자가 공지게시판의 글을 등록하는 API")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity<BaseResponse<PostDto.CreateResponse>> createPost(@RequestParam(value = "category", required = false) Long categoryId,
                                                                           @RequestBody PostDto.CreateRequest createRequest) {
        return ResponseEntity.ok(BaseResponse.create(
                CREATE_POST_SUCCESS.getMessage(), postService.createNewPost(createRequest, PostRole.NOTICE, categoryId)));
    }

    @ApiOperation(value = "공지게시판 글 수정", notes = "관리자가 공지게시판의 글을 수정하는 API")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PatchMapping("/{postId}")
    public ResponseEntity<BaseResponse<PostDto.UpdateResponse>> updatePost(@PathVariable("postId") Long postId,
                                                                           @RequestBody PostDto.UpdateRequest updateRequest) {
        return ResponseEntity.ok(BaseResponse.create(
                UPDATE_POST_SUCCESS.getMessage(), postService.updatePost(updateRequest, postId)));
    }

    @ApiOperation(value = "공지게시판 글 조회", notes = "공지게시판의 글 상세정보를 조회하는 API")
    @GetMapping("/{postId}")
    public ResponseEntity<BaseResponse<PostDto.SearchResponse>> searchPostDetail(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok(BaseResponse.create(
                GET_POST_DETAIL_SUCCESS.getMessage(), postService.searchPostDetail(postId)));
    }

    @ApiOperation(value = "공지게시판 리스트 조회", notes = "공지게시판의 글 전체 리스트를 조회하는 API")
    @GetMapping
    public ResponseEntity<BaseResponse<SliceDto<PostDto.SearchResponse>>> searchPostList(@RequestParam("role") String postRole,
                                                                                         @RequestParam(value = "category", required = false) Long categoryId,
                                                                                         @PageableDefault(size = 100) Pageable pageable) {
        return ResponseEntity.ok(BaseResponse.create(
                GET_POST_LIST_SUCCESS.getMessage(), postService.searchPostList(postRole, categoryId, pageable)));
    }

    @ApiOperation(value = "공지게시판 글 삭제", notes = "관리자가 공지게시판의 글을 삭제하는 API")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{postId}")
    public ResponseEntity<BaseResponse<String>> deletePost(@PathVariable("postId") Long postId) {
        postService.findPostAndDelete(postId);
        return ResponseEntity.ok(BaseResponse.create(DELETE_POST_SUCCESS.getMessage()));
    }
}
