package com.example.carefully.domain.user.dto;

import com.example.carefully.domain.user.entity.Address;
import com.example.carefully.domain.user.entity.Gender;
import com.example.carefully.domain.user.entity.User;
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

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 3, max = 100)
    private String password;

    @NotNull
    @Size(min = 3, max = 50)
    private String email;

    @NotNull
    @Size(min = 3, max = 50)
    private String phoneNumber;

    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    @NotNull
    private Gender gender;

    @NotNull
    private Address address;

    @NotNull
    private String university;

    @NotNull
    private RoleRequest role;

    public static RegisterRequest from(User user) {
        if(user == null) return null;

        return RegisterRequest.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .name(user.getName())
                .gender(user.getGender())
                .address(user.getAddress())
                .university(user.getUniversity())
                .role(RoleRequest.valueOf(user.getRole().name()))
                .build();
    }

}

