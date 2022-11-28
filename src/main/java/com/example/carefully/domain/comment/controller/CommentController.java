package com.example.carefully.domain.comment.controller;

import com.example.carefully.domain.comment.dto.CommentDto;
import com.example.carefully.domain.comment.service.CommentService;
import com.example.carefully.global.dto.BaseResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.carefully.domain.comment.dto.CommentResponseMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    @ApiOperation(value = "댓글 생성", notes = "댓글을 생성하는 API")
    @PostMapping
    public ResponseEntity<BaseResponse<CommentDto.CreateResponse>> createComment(@RequestBody CommentDto.CreateRequest request) {
        return ResponseEntity.ok(BaseResponse.create(
                CREATE_COMMENT_SUCCESS.getMessage(), commentService.addComment(request)));
    }

    @ApiOperation(value = "모든 댓글 조회", notes = "각 게시글의 댓글을 조회하는 API")
    @GetMapping("/{postId}")
    public ResponseEntity<BaseResponse<CommentDto.SearchResponse>> findComments(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok(BaseResponse.create(
                SEARCH_COMMENTS_SUCCESS.getMessage(), commentService.findAllCommentsByPost(postId)));
    }

    @ApiOperation(value = "댓글 삭제", notes = "댓글을 삭제하는 API")
    @DeleteMapping("/{commentId}")
    public  ResponseEntity<BaseResponse<String>> deleteComment(@PathVariable("commentId") Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok(BaseResponse.create(DELETE_COMMENTS_SUCCESS.getMessage()));
    }
}
