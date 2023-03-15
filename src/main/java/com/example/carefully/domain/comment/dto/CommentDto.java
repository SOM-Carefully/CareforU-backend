package com.example.carefully.domain.comment.dto;

import com.example.carefully.domain.comment.domain.Comment;
import com.example.carefully.domain.comment.domain.Comment.CommentStatus;
import com.example.carefully.domain.post.domain.Post;
import com.example.carefully.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommentDto {

    @Getter
    public static class CreateRequest {
        @Schema(description = "게시글 아이디", example = "1", required = true)
        private Long postId;

        @Schema(description = "부모 댓글 아이디", example = "1")
        private Long parentId;

        @Schema(description = "댓글 내용", example = "댓글입니다", required = true)
        private String content;

        @Schema(description = "댓글 계층", example = "PARENT/CHILD", required = true)
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
            @Schema(description = "부모 댓글 정보")
            private final CommentResponse parent;

            @Schema(description = "자식 댓글 정보 리스트")
            private final List<CommentResponse> children = new ArrayList<>();

            public ReplyResponse(CommentResponse parent) {
                this.parent = parent;
            }
        }
    }

    @Getter
    @Builder
    public static class CommentResponse {
        @Schema(description = "댓글 아이디")
        private Long commentId;

        @Schema(description = "댓글 내용")
        private String content;

        @Schema(description = "댓글 상태", example = "ALIVE/DELETED")
        private CommentStatus status;

        @Schema(description = "댓글 생성 시간")
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
