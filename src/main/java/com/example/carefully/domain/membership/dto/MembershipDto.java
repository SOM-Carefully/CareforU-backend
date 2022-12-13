package com.example.carefully.domain.membership.dto;

import com.example.carefully.domain.membership.entity.Membership;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class MembershipDto {
    @Getter
    @RequiredArgsConstructor
    public enum StateRequest {
        ACCEPT, WAITING, REJECT;
    }

    @Getter
    @RequiredArgsConstructor
    public enum RoleRequest {
        CLASSIC, SILVER, GOLD, PLATINUM, ADMIN;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MembershipResponse {
        @NotNull
        @ApiModelProperty(example = "2018-07-26T01:20:00")
        private LocalDateTime createdAt;

        @NotNull
        @ApiModelProperty(example = "TRANSLATE/DWELLING/TRAFFIC")
        private StateRequest stateRequest;

        @ApiModelProperty(example = "회원가입 신청한 유저 이메일")
        private String requestUsername;

        @ApiModelProperty(example = "CLASSIC/SILVER/GOLD/PLATINUM/AMDIN")
        private RoleRequest requestRole;

        @ApiModelProperty(example = "회원가입 신청에 대해 처리한 어드민 이메일")
        private String adminUsername;

        @ApiModelProperty(example = "회원가입 신청합니다!")
        private String content;

        public static MembershipResponse create(Membership membership) {
            String adminUsername = null;
            if (membership.getAdmin() != null) {
                adminUsername = membership.getAdmin().getUsername();
            }
            return MembershipResponse.builder()
                    .createdAt(membership.getCreatedAt())
                    .stateRequest(StateRequest.valueOf(membership.getMembershipStatus().name()))
                    .requestUsername(membership.getUser().getUsername())
                    .requestRole(RoleRequest.valueOf(membership.getUser().getRole().name()))
                    .adminUsername(adminUsername)
                    .content(membership.getContent())
                    .build();
        }
    }
}
