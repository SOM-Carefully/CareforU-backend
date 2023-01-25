package com.example.carefully.domain.user.dto;

import com.example.carefully.domain.user.entity.User;
import lombok.*;

@Getter
@Builder
public class TokenResponse {

    @Builder
    @Getter
    public static class TokenInfo {
        private String grantType;
        private String accessToken;
        private String refreshToken;
        private Long refreshTokenExpirationTime;

        public static TokenInfo create(TokenInfo tokenInfo) {
            return TokenInfo.builder()
                    .grantType(tokenInfo.getGrantType())
                    .accessToken(tokenInfo.getAccessToken())
                    .refreshToken(tokenInfo.getRefreshToken())
                    .refreshTokenExpirationTime(tokenInfo.getRefreshTokenExpirationTime())
                    .build();
        }
    }
}
