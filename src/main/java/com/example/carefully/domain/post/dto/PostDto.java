package com.example.carefully.domain.post.dto;

import com.example.carefully.domain.category.domain.Category;
import com.example.carefully.domain.post.domain.Post;
import com.example.carefully.domain.post.domain.PostRole;
import com.example.carefully.domain.user.entity.User;
import com.example.carefully.global.common.Constant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
public class PostDto {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class CreateRequest {
        @Schema(description = "게시글 제목", required = true)
        private String title;

        @Schema(description = "게시글 내용", required = true)
        private String content;

        @Schema(description = "S3에서 불러온 이미지 경로")
        private String imgUrl;

        public Post toEntity(PostRole role, Category category, User user) {
            return Post.builder()
                    .postRole(role)
                    .user(user)
                    .category(category)
                    .title(title)
                    .content(content)
                    .imgUrl(imgUrl)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class CreateResponse {
        private Long postId;
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class UpdateRequest {
        @Schema(description = "게시글 제목", required = true)
        private String title;

        @Schema(description = "게시글 내용", required = true)
        private String content;

        @Schema(description = "S3에서 불러온 이미지 경로")
        private String imgUrl;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class UpdateResponse {
        private Long postId;
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class SearchResponse{
        private Long postId;
        private String title;
        private String content;
        private String writer;
        private String imgUrl;
        private String createdAt;

        public static SearchResponse create(Post post) {
            return SearchResponse.builder()
                    .postId(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .writer(post.getUser().getName())
                    .imgUrl(post.getImgUrl())
                    .createdAt(post.getCreatedAt().format(Constant.formatter)).build();
        }
    }
}
