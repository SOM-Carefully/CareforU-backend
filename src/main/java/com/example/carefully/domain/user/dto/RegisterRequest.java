package com.example.carefully.domain.user.dto;

import com.example.carefully.domain.user.entity.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotNull
    @Size(min = 3, max = 50)
    @ApiModelProperty(example = "유저 이메일")
    private String username;

    @NotNull
    @ApiModelProperty(example = "유저 이름")
    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 3, max = 100)
    @ApiModelProperty(example = "유저 비밀번호")
    private String password;

    @NotNull
    @Size(min = 3, max = 50)
    @ApiModelProperty(example = "일반 유저 전화번호")
    private String phoneNumber;

    @ApiModelProperty(example = "일반 유저 외국인등록번호")
    private String foreignerNumber;

    @ApiModelProperty(example = "일반 유저 성별")
    private Gender gender;

    @ApiModelProperty(example = "일반 유저 주소")
    private Address address;

    @ApiModelProperty(example = "일반 유저 대학")
    private String university;

    @ApiModelProperty(example = "일반 유저 전공")
    private String major;

    @ApiModelProperty(example = "운영팀 유저 사업자 종류")
    private BusinessType businessType;

    @ApiModelProperty(example = "운영팀 유저 상호명")
    private String businessName;

    @ApiModelProperty(example = "운영팀 유저 사업자 등록 번호")
    private String businessRegisterNumber;

    @NotNull
    @ApiModelProperty(example = "유저 권한")
    private RoleRequest role;

    public static RegisterRequest fromUser(User user) {
        if(user == null) return null;

        return RegisterRequest.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .foreignerNumber(user.getForeignerNumber())
                .gender(user.getGender())
                .address(user.getAddress())
                .university(user.getUniversity())
                .major(user.getMajor())
                .role(RoleRequest.valueOf(user.getRole().name()))
                .build();
    }

    public static RegisterRequest fromOperation(Operation operation) {
        if (operation == null) return null;

        return RegisterRequest.builder()
                .username(operation.getUsername())
                .password(operation.getPassword())
                .name(operation.getName())
                .phoneNumber(operation.getPhoneNumber())
                .businessType(operation.getBusinessType())
                .businessName(operation.getBusinessName())
                .businessRegisterNumber(operation.getBusinessRegisterNumber())
                .role(RoleRequest.valueOf(operation.getRole().name()))
                .build();
    }

    public static RegisterRequest fromAdmin(Admin admin) {
        if (admin == null) return null;

        return RegisterRequest.builder()
                .username(admin.getUsername())
                .password(admin.getPassword())
                .name(admin.getName())
                .phoneNumber(admin.getPhoneNumber())
                .role(RoleRequest.valueOf(admin.getRole().name()))
                .build();
    }

}

