package com.example.carefully.domain.post.dto;

import com.example.carefully.domain.post.domain.Post;
import com.example.carefully.domain.post.domain.PostRole;
import lombok.*;

public class QuestionDto {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class CreateRequest {
        private String title;
        private String content;
        private boolean locked;

        public Post toEntity(PostRole role, Long userId) {
            return Post.builder()
                    .postRole(role)
                    .userId(userId)
                    .title(title)
                    .content(content)
                    .locked(locked)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class CreateResponse {
        private Long questionId;
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class UpdateRequest {
        private String title;
        private String content;
        private boolean locked;
    }

}
