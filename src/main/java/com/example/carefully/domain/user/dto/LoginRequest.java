package com.example.carefully.domain.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    
    @NotNull
    @Size(min = 3, max = 50)
    @ApiModelProperty(example = "유저 이메일")
    private String username;

    @NotNull
    @Size(min = 3, max = 100)
    @ApiModelProperty(example = "유저 비밀번호")
    private String password;
}