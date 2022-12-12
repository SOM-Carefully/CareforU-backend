package com.example.carefully.domain.comment.dto;

import com.example.carefully.domain.comment.domain.Comment;
import com.example.carefully.domain.comment.domain.Comment.CommentStatus;
import com.example.carefully.domain.post.domain.Post;
import com.example.carefully.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommentDto {

    @Getter
    public static class CreateRequest {
        private Long postId;
        private Long parentId;
        private String content;
        private String hierarchy;

        public Comment toEntity(Post post, User user) {
            return Comment.builder()
                    .user(user)
                    .post(post)
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

    @Getter
    @AllArgsConstructor
    public static class SearchResponse {
        private final List<ReplyResponse> commentList;

        @Getter
        public static class ReplyResponse {
            private final CommentResponse parent;
            private final List<CommentResponse> children = new ArrayList<>();

            public ReplyResponse(CommentResponse parent) {
                this.parent = parent;
            }

            @Getter
            @Builder
            public static class CommentResponse {
                private Long commentId;
                private String content;
                private CommentStatus status;
                @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm", timezone = "Asia/Seoul")
                private LocalDateTime createdAt;

                public static CommentResponse of(Comment comment) {
                    return CommentResponse.builder()
                            .commentId(comment.getId())
                            .content(comment.getContent())
                            .status(comment.getCommentStatus())
                            .createdAt(comment.getCreatedAt())
                            .build();
                }
            }
        }
    }
}
