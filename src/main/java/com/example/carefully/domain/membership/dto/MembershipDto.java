package com.example.carefully.domain.membership.dto;

import com.example.carefully.domain.membership.entity.Membership;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
        LEVEL1, LEVEL2, LEVEL3, LEVEL4, LEVEL5, ADMIN;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MembershipResponse {
        @Schema(description = "회원가입 신청 고유 번호", example = "1", required = true)
        private Long membershipId;

        @NotNull
        @ApiModelProperty(example = "2018-07-26T01:20:00")
        private LocalDateTime createdAt;

        @NotNull
        @ApiModelProperty(example = "ACCEPT/WAITING/REJECT")
        private StateRequest stateRequest;

        @ApiModelProperty(example = "회원가입 신청한 유저 이메일")
        private String requestUsername;

        @ApiModelProperty(example = "LEVEL1/LEVEL2/LEVEL3/LEVEL4/LEVEL5/ADMIN")
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
                    .membershipId(membership.getId())
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
