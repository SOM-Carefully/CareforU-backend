package com.example.carefully.domain.category.dto;

import com.example.carefully.domain.category.domain.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

public class CategoryDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class CreateRequest {
        @Schema(description = "카테고리 이름", required = true)
        private String categoryName;

        public Category toEntity() {
            return new Category(categoryName);
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class CreateResponse {
        private Long categoryId;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class UpdateRequest {
        @Schema(description = "카테고리 이름", required = true)
        private String categoryName;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static class CategoryResponse {
        private Long categoryId;
        private String categoryName;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class SearchResponse {
        private List<CategoryResponse> categoryResponses;
    }
}
