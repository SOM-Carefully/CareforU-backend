package com.example.carefully.domain.comment.controller;

import com.example.carefully.domain.comment.dto.CommentDto;
import com.example.carefully.domain.comment.service.CommentService;
import com.example.carefully.global.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.carefully.domain.comment.dto.CommentResponseMessage.CREATE_COMMENT_SUCCESS;
import static com.example.carefully.domain.comment.dto.CommentResponseMessage.SEARCH_COMMENTS_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<BaseResponse<CommentDto.CreateResponse>> createComment(@RequestBody CommentDto.CreateRequest request) {
        return ResponseEntity.ok(BaseResponse.create(
                CREATE_COMMENT_SUCCESS.getMessage(), commentService.addComment(request)));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<BaseResponse<CommentDto.SearchResponse>> findComments(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok(BaseResponse.create(
                SEARCH_COMMENTS_SUCCESS.getMessage(), commentService.findAllCommentsByPost(postId)));
    }
}
