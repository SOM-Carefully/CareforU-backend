package com.example.carefully.domain.comment.dto;

import com.example.carefully.domain.comment.domain.Comment;
import com.example.carefully.domain.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class CommentDto {

    @Getter
    public static class CreateRequest {
        private Long postId;
        private Long parentId;
        private String content;
        private String hierarchy;

        public Comment toEntity(Post post, Comment parent) {
            return Comment.builder()
                    .userId(1L)
                    .post(post)
                    .parent(parent)
                    .content(content)
                    .hierarchy(Comment.Hierarchy.valueOf(hierarchy))
                    .build();
        }

        public boolean isRequestParent() {
            return hierarchy.equals(Comment.Hierarchy.PARENT.name());
        }
    }

    @Getter
    @AllArgsConstructor
    public static class CreateResponse {
        private Long commentId;
    }
}
