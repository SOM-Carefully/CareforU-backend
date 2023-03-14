package com.example.carefully.domain.comment.service;

import com.example.carefully.domain.comment.domain.Comment;
import com.example.carefully.domain.comment.dto.CommentDto.SearchResponse.ReplyResponse;
import com.example.carefully.domain.comment.exception.CommentEmptyException;
import com.example.carefully.domain.comment.repository.CommentRepository;
import com.example.carefully.domain.comment.repository.CustomCommentRepository;
import com.example.carefully.domain.post.domain.Post;
import com.example.carefully.domain.post.exception.PostEmptyException;
import com.example.carefully.domain.post.repository.PostRepository;
import com.example.carefully.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.carefully.domain.comment.dto.CommentDto.*;
import static com.example.carefully.global.utils.UserUtils.getCurrentUser;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CustomCommentRepository customCommentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    /**
     * 댓글을 등록한다.
     *
     * @param request 게시글 ID/ 부모 댓글 ID/ 댓글 내용/ 댓글 계층
     * @return 새롭게 생성된 댓글의 ID
     */
    @Override
    @Transactional
    public CreateResponse addComment(CreateRequest request) {
        Post post = postRepository.findById(request.getPostId()).orElseThrow(PostEmptyException::new);
        Comment comment = commentRepository.save(request.toEntity(post, getCurrentUser(userRepository)));
        comment.addParent(getParentOrChild(request));
        return new CreateResponse(comment.getId());
    }

    private Comment getParentOrChild(CreateRequest request) {
        if (request.isRequestParent()) {
            return null;
        }
        return getCommentByIdAndUser(request.getParentId());
    }

    /**
     * 게시글의 모든 댓글을 조회한다.
     *
     * @param postId 댓글을 조회하고 싶은 게시글 ID
     * @return 해당 게시글의 부모 댓글과 대댓글 정보가 쌍으로 담긴 리스트
     */
    @Override
    public SearchResponse findAllCommentsByPost(Long postId) {
        List<Comment> commentsByPostId = customCommentRepository.findCommentsByPostId(postId);
        Map<Long, ReplyResponse> commentMap = createCommentMap(commentsByPostId);
        return new SearchResponse(new ArrayList<>(commentMap.values()));
    }

    private Map<Long, ReplyResponse> createCommentMap(List<Comment> comments) {
        Map<Long, ReplyResponse> commentResultMap = new HashMap<>();
        comments.forEach(c -> {
            CommentResponse comment = CommentResponse.of(c);
            if (c.isParent()) {
                commentResultMap.put(c.getId(), new ReplyResponse(comment));
            } else {
                commentResultMap.get(c.getParent().getId())
                        .getChildren()
                        .add(comment);
            }
        });
        return commentResultMap;
    }

    /**
     * 댓글을 삭제한다.
     *
     * @param commentId 삭제하려는 댓글의 ID
     */
    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = getCommentByIdAndUser(commentId);
        if (comment.canDeleteComment()) {
            commentRepository.delete(comment);
        } else {
            comment.changeStatusToDeleted();
        }
    }

    private Comment getCommentByIdAndUser(Long commentId) {
        return commentRepository.findByIdAndUser(commentId, getCurrentUser(userRepository))
                .orElseThrow(CommentEmptyException::new);
    }
}
