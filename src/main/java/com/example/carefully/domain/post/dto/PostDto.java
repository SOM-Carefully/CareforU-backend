package com.example.carefully.domain.post.dto;

import com.example.carefully.domain.post.domain.Post;
import com.example.carefully.domain.post.domain.PostRole;
import com.example.carefully.global.common.Constant;
import lombok.*;

@Getter
public class PostDto {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class CreateRequest{
        private String title;
        private String content;
        private String imgUrl;

        public Post toEntity(PostRole role, Long userId){
            return Post.builder()
                    .postRole(role)
                    .userId(userId)
                    .title(title)
                    .content(content)
                    .imgUrl(imgUrl)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class CreateResponse{
        private Long postId;
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class UpdateRequest{
        private String title;
        private String content;
        private String imgUrl;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class UpdateResponse{
        private Long postId;
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class SearchResponse{
        private Long postId;
        private String title;
        private String writer;
        private String imgUrl;
        private String createdAt;

        public static SearchResponse create(Post post){
            return SearchResponse.builder()
                    .postId(post.getId())
                    .title(post.getTitle())
                    .writer(post.getUserId().toString())
                    .imgUrl(post.getImgUrl())
                    .createdAt(post.getCreatedAt().format(Constant.formatter)).build();
        }
    }
}
