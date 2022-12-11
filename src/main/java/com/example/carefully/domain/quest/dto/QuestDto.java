package com.example.carefully.domain.quest.dto;

import com.example.carefully.domain.quest.domain.Quest;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

public class QuestDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class CreateRequest {
        private String title;
        private String content;
        private boolean locked;

        public Quest toEntity(Long userId) {
            return Quest.builder()
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
        private String answer;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm", timezone = "Asia/Seoul")
        private LocalDateTime createdAt;

        public static QuestDto.SearchResponse create(Quest quest) {
            return SearchResponse.builder()
                    .questionId(quest.getId())
                    .title(quest.getTitle())
                    .content(quest.getContent())
                    .writer(quest.getUserId().toString())
                    .locked(quest.getLocked())
                    .answer(quest.getAnswer())
                    .createdAt(quest.getCreatedAt()).build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class AnswerRequest {
        private String content;
    }
}
