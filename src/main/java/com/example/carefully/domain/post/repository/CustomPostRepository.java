package com.example.carefully.domain.post.repository;

import com.example.carefully.domain.post.domain.Post;
import com.example.carefully.domain.post.domain.PostRole;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.carefully.domain.post.domain.QPost.post;

@Repository
@RequiredArgsConstructor
public class CustomPostRepository {
    private final JPAQueryFactory queryFactory;

    public Slice<Post> getPostList(Pageable pageable, PostRole postRole, Long categoryId) {
        List<Post> posts = queryFactory.selectFrom(post)
                .where(postRoleEq(postRole), categoryEq(categoryId))
                .orderBy(post.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        return new SliceImpl<>(posts, pageable, hasNext(posts, pageable));
    }

    private boolean hasNext(List<Post> content, Pageable pageable) {
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            return true;
        }
        return false;
    }

    private BooleanExpression postRoleEq(PostRole postRole) {
        return post.postRole.eq(postRole);
    }

    private BooleanExpression categoryEq(Long category) {
        if (category == null) {
            return null;
        }
        return post.category.id.eq(category);
    }
}
