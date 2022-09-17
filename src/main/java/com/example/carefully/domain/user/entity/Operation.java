package com.example.carefully.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Enumerated;

import static javax.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

@Entity
@DiscriminatorValue("Operation")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Operation extends CommonUser{
    @Column(name ="businessType")
    @Enumerated(value = STRING)
    private BusinessType businessType;

    @Column(name = "businessName")
    private String businessName;

    @Column(name = "businessRegisterNumber")
    private String businessRegisterNumber;

    @Builder
    public Operation(String username, String name, String phoneNumber, String password, boolean activated, Role role,
                     BusinessType businessType, String businessName, String businessRegisterNumber) {
        super(username, name, phoneNumber, password, role, activated);
        this.businessType = businessType;
        this.businessName = businessName;
        this.businessRegisterNumber = businessRegisterNumber;
    }
}
