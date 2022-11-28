package com.example.carefully.domain.comment.repository;

import com.example.carefully.domain.comment.domain.Comment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.carefully.domain.comment.domain.QComment.comment;

@Repository
@RequiredArgsConstructor
public class CustomCommentRepository {

    private final JPAQueryFactory queryFactory;

    public List<Comment> findCommentsByPostId(Long postId) {
        return queryFactory.selectFrom(comment)
                .where(comment.post.id.eq(postId))
                .orderBy(
                        comment.parent.id.asc().nullsFirst(),
                        comment.createdAt.asc())
                .fetch();
    }
}
