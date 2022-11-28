package com.example.carefully.domain.comment.service;

import com.example.carefully.domain.comment.domain.Comment;
import com.example.carefully.domain.comment.dto.CommentDto;
import com.example.carefully.domain.comment.dto.CommentDto.SearchResponse.ReplyResponse;
import com.example.carefully.domain.comment.exception.CommentEmptyException;
import com.example.carefully.domain.comment.repository.CommentRepository;
import com.example.carefully.domain.comment.repository.CustomCommentRepository;
import com.example.carefully.domain.post.domain.Post;
import com.example.carefully.domain.post.exception.PostEmptyException;
import com.example.carefully.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CustomCommentRepository customCommentRepository;
    private final PostRepository postRepository;

    @Transactional
    @Override
    public CommentDto.CreateResponse addComment(CommentDto.CreateRequest request) {
        Post post = postRepository.findById(request.getPostId()).orElseThrow(PostEmptyException::new);
        Comment comment = commentRepository.save(request.toEntity(post));
        comment.addParent(getParentOrChild(request));

        return new CommentDto.CreateResponse(comment.getId());
    }

    private Comment getParentOrChild(CommentDto.CreateRequest request) {
        if (request.isRequestParent()) {
            return null;
        }
        return commentRepository.findById(request.getParentId()).orElseThrow(CommentEmptyException::new);
    }

    @Override
    public CommentDto.SearchResponse findAllCommentsByPost(Long postId) {
        List<Comment> commentsByPostId = customCommentRepository.findCommentsByPostId(postId);
        Map<Long, ReplyResponse> commentMap = createCommentMap(commentsByPostId);

        return new CommentDto.SearchResponse(new ArrayList<>(commentMap.values()));
    }

    private Map<Long, ReplyResponse> createCommentMap(List<Comment> comments) {
        Map<Long, ReplyResponse> map = new HashMap<>();
        comments.forEach(c -> {
            ReplyResponse.CommentResponse comment = ReplyResponse.CommentResponse.of(c);
            if (c.isParent()) {
                map.put(c.getId(), new ReplyResponse(comment));
            } else {
                map.get(c.getParent().getId()).getChildren().add(comment);
            }
        });
        return map;
    }

    @Transactional
    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentEmptyException::new);

        if (comment.canDeleteComment()) {
            commentRepository.delete(comment);
        } else {
            comment.changeStatusToDeleted();
        }
    }
}
