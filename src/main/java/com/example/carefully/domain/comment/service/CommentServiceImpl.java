package com.example.carefully.domain.comment.service;

import com.example.carefully.domain.comment.domain.Comment;
import com.example.carefully.domain.comment.dto.CommentDto;
import com.example.carefully.domain.comment.exception.CommentEmptyException;
import com.example.carefully.domain.comment.repository.CommentRepository;
import com.example.carefully.domain.post.domain.Post;
import com.example.carefully.domain.post.exception.PostEmptyException;
import com.example.carefully.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    @Override
    public CommentDto.CreateResponse addComment(CommentDto.CreateRequest request) {
        Post post = postRepository.findById(request.getPostId()).orElseThrow(PostEmptyException::new);
        Comment comment = commentRepository.save(
                request.toEntity(post, getParentOrChild(request)));

        return new CommentDto.CreateResponse(comment.getId());
    }

    private Comment getParentOrChild(CommentDto.CreateRequest request) {
        if (request.isRequestParent()) {
            return null;
        }
        return commentRepository.findById(request.getParentId()).orElseThrow(CommentEmptyException::new);
    }
}
