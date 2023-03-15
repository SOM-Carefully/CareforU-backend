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

    /**
     * 게시글 댓글 리스트를 조회한다.
     *
     * @param postId 게시글 ID
     * @return 부모 댓글이 먼저 오고 최신 작성순으로 정렬된 게시글의 모든 댓글 리스트
     */
    public List<Comment> findCommentsByPostId(Long postId) {
        return queryFactory.selectFrom(comment)
                .where(comment.post.id.eq(postId))
                .orderBy(
                        comment.parent.id.asc().nullsFirst(),
                        comment.createdAt.asc())
                .fetch();
    }
}
