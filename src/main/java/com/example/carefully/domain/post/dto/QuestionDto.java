package com.example.carefully.domain.post.dto;

import com.example.carefully.domain.post.domain.Post;
import com.example.carefully.domain.post.domain.PostRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

public class QuestionDto {

    @Getter
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
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class UpdateRequest {
        private String title;
        private String content;
        private boolean locked;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static class SearchResponse {
        private Long questionId;
        private String title;
        private String content;
        private String writer;
        private String locked;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm", timezone = "Asia/Seoul")
        private LocalDateTime createdAt;

        public static QuestionDto.SearchResponse create(Post post) {
            return SearchResponse.builder()
                    .questionId(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .writer(post.getUserId().toString())
                    .locked(post.getLocked())
                    .createdAt(post.getCreatedAt()).build();
        }
    }
}
