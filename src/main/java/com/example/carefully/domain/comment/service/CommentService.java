package com.example.carefully.domain.comment.service;

import com.example.carefully.domain.comment.dto.CommentDto;

public interface CommentService {
    CommentDto.CreateResponse addComment(CommentDto.CreateRequest request);
    CommentDto.SearchResponse findAllCommentsByPost(Long postId);
}
