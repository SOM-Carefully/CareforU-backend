package com.example.carefully.domain.post.dto;

import com.example.carefully.domain.post.domain.Category;
import lombok.*;

import java.util.List;

public class CategoryDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class CreateRequest {
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
