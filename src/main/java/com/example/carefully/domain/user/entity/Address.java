package com.example.carefully.domain.user.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class Address {

    @ApiModelProperty(example = "서울특별시 종로구 홍지문2길 20")
    private String details;
}
