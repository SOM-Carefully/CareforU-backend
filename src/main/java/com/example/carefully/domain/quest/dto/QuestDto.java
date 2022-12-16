package com.example.carefully.domain.quest.dto;

import com.example.carefully.domain.quest.domain.Quest;
import com.example.carefully.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

public class QuestDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class CreateRequest {

        @Schema(description = "문의 제목", required = true)
        private String title;

        @Schema(description = "문의 내용", required = true)
        private String content;

        @Schema(description = "공개/비공개 여부", required = true)
        private boolean locked;

        public Quest toEntity(User user) {
            return Quest.builder()
                    .user(user)
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
        @Schema(description = "문의 제목", required = true)
        private String title;

        @Schema(description = "문의 내용", required = true)
        private String content;

        @Schema(description = "공개/비공개 여부", required = true)
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

        @Schema(description = "답변 내용")
        private String answer;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm", timezone = "Asia/Seoul")
        private LocalDateTime createdAt;

        public static QuestDto.SearchResponse create(Quest quest) {
            return SearchResponse.builder()
                    .questionId(quest.getId())
                    .title(quest.getTitle())
                    .content(quest.getContent())
                    .writer(quest.getUser().getName())
                    .locked(quest.getLocked())
                    .answer(quest.getAnswer())
                    .createdAt(quest.getCreatedAt()).build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class AnswerRequest {
        @Schema(description = "관리자 답변 내용", required = true)
        private String content;
    }
}
