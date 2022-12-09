package com.example.carefully.domain.user.dto;

import com.example.carefully.domain.booking.entity.Booking;
import com.example.carefully.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Builder
public class UserDto {

    @Getter
    @RequiredArgsConstructor
    public enum RoleRequest {
        CLASSIC, SILVER, GOLD, PLATINUM;
    }

    @Getter
    @RequiredArgsConstructor
    public enum EducationRequest {
        UNDERGRADUATE, BACHELOR, MASTER, DOCTOR;
    }

    @Getter
    @RequiredArgsConstructor
    public enum MembershipRequest {
        ACCEPT, WAITING, REJECT;
    }

    @Getter
    @RequiredArgsConstructor
    public enum GenderRequest {
        MAN, WOMAN, NA;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserRegisterRequest {

        @NotNull
        @Size(min = 3, max = 50)
        @ApiModelProperty(example = "test@test.com")
        private String username;

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @NotNull
        @Size(min = 3, max = 100)
        @ApiModelProperty(example = "test1234")
        private String password;

        @NotNull
        @ApiModelProperty(example = "홍길동")
        private String name;

        @NotNull
        @ApiModelProperty(example = "123456-1234567")
        private String identificationNumber;

        @NotNull
        @ApiModelProperty(example = "010-1234-5678")
        private String phoneNumber;

        @NotNull
        @ApiModelProperty(example = "상명대학교")
        private String universityName;

        @NotNull
        @ApiModelProperty(example = "UNDERGRADUATE/BACHELOR/MASTER/DOCTOR")
        private EducationRequest educationRequest;

        @NotNull
        @ApiModelProperty(example = "MAN/WOMAN/NA")
        private GenderRequest genderRequest;

        @NotNull
        @ApiModelProperty(example = "CLASSIC/SILVER/GOLD/PLATINUM")
        private RoleRequest role;

        @ApiModelProperty(example = "회원가입 신청합니다!")
        private String content;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AdminRegisterRequest {

        @NotNull
        @Size(min = 3, max = 50)
        @ApiModelProperty(example = "test@test.com")
        private String username;

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @NotNull
        @Size(min = 3, max = 100)
        @ApiModelProperty(example = "test1234")
        private String password;

        @NotNull
        @ApiModelProperty(example = "홍길동")
        private String name;

        @NotNull
        @ApiModelProperty(example = "123456-1234567")
        private String identificationNumber;

        @NotNull
        @ApiModelProperty(example = "010-1234-5678")
        private String phoneNumber;

        @NotNull
        @ApiModelProperty(example = "MAN/WOMAN/NA")
        private GenderRequest genderRequest;

        @NotNull
        @ApiModelProperty(example = "123-12-12345")
        String businessRegistrationNumber;

        @ApiModelProperty(example = "회원가입 신청합니다!")
        private String content;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserResponse {
        @NotNull
        @Size(min = 3, max = 50)
        @ApiModelProperty(example = "test@test.com")
        private String username;

        @NotNull
        @ApiModelProperty(example = "홍길동")
        private String name;

        @NotNull
        @ApiModelProperty(example = "123456-1234567")
        private String identificationNumber;

        @NotNull
        @ApiModelProperty(example = "010-1234-5678")
        private String phoneNumber;

        @NotNull
        @ApiModelProperty(example = "상명대학교")
        private String universityName;

        @NotNull
        @ApiModelProperty(example = "UNDERGRADUATE/BACHELOR/MASTER/DOCTOR")
        private EducationRequest education;

        @NotNull
        @ApiModelProperty(example = "MAN/WOMAN/NA")
        private GenderRequest gender;

        @NotNull
        @ApiModelProperty(example = "CLASSIC/SILVER/GOLD/PLATINUM")
        private RoleRequest role;

        public static UserResponse create(User user) {
            return UserResponse.builder()
                    .username(user.getUsername())
                    .name(user.getName())
                    .identificationNumber(user.getIdentificationNumber())
                    .phoneNumber(user.getPhoneNumber())
                    .universityName(user.getUniversityName())
                    .education(EducationRequest.valueOf(user.getEducation().name()))
                    .gender(GenderRequest.valueOf(user.getGender().name()))
                    .role(RoleRequest.valueOf(user.getRole().name()))
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AdminResponse {
        @NotNull
        @Size(min = 3, max = 50)
        @ApiModelProperty(example = "test@test.com")
        private String username;

        @NotNull
        @ApiModelProperty(example = "홍길동")
        private String name;

        @NotNull
        @ApiModelProperty(example = "123456-1234567")
        private String identificationNumber;

        @NotNull
        @ApiModelProperty(example = "010-1234-5678")
        private String phoneNumber;

        @NotNull
        @ApiModelProperty(example = "123-12-12345")
        String businessRegistrationNumber;

        @NotNull
        @ApiModelProperty(example = "MAN/WOMAN/NA")
        private GenderRequest gender;

        String role;

        public static AdminResponse create(User user) {
            return AdminResponse.builder()
                    .username(user.getUsername())
                    .name(user.getName())
                    .identificationNumber(user.getIdentificationNumber())
                    .phoneNumber(user.getPhoneNumber())
                    .businessRegistrationNumber(user.getBusinessRegistrationNumber())
                    .gender(GenderRequest.valueOf(user.getGender().name()))
                    .role(user.getRole().name())
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginRequest {

        @NotNull
        @Size(min = 3, max = 50)
        @ApiModelProperty(example = "test@test.com")
        private String username;

        @NotNull
        @Size(min = 3, max = 100)
        @ApiModelProperty(example = "test1234")
        private String password;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SignoutRequest {

        @NotNull
        @Size(min = 3, max = 100)
        @ApiModelProperty(example = "test1234")
        private String password;
    }
}
