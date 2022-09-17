package com.example.carefully.domain.user.dto;

import com.example.carefully.domain.user.entity.*;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    private String username;

    @NotNull
    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 3, max = 100)
    private String password;

    @NotNull
    @Size(min = 3, max = 50)
    private String phoneNumber;

    private String foreignerNumber;

    private Gender gender;

    private Address address;

    private String university;

    private String major;

    private BusinessType businessType;

    private String businessName;

    private String businessRegisterNumber;

    @NotNull
    private RoleRequest role;

    public static RegisterRequest fromUser(User user) {
        if(user == null) return null;

        return RegisterRequest.builder()
                .username(user.getUsername())
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
                .name(operation.getName())
                .phoneNumber(operation.getPhoneNumber())
                .businessType(operation.getBusinessType())
                .businessName(operation.getBusinessName())
                .businessRegisterNumber(operation.getBusinessRegisterNumber())
                .role(RoleRequest.valueOf(operation.getRole().name()))
                .build();
    }

}

