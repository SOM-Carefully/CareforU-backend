package com.example.carefully.domain.post.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Embeddable
public class PostCategoryId implements Serializable {

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "category_id")
    private Long categoryId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PostCategoryId id = (PostCategoryId) o;
        return Objects.equals(postId, id.getPostId()) && Objects.equals(categoryId, id.getCategoryId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, categoryId);
    }
}
