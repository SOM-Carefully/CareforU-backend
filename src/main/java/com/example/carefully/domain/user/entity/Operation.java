package com.example.carefully.domain.user.entity;

import com.example.carefully.domain.user.dto.UserDto;
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
public class Operation extends User {
    @Column(name ="businessType")
    @Enumerated(value = STRING)
    private BusinessType businessType;

    @Column(name = "businessName")
    private String businessName;

    @Column(name = "businessRegisterNumber")
    private String businessRegisterNumber;

    @Builder
    public Operation(Operation operation, String username, String name, String phoneNumber, String password, boolean activated, Role role,
                     BusinessType businessType, String businessName, String businessRegisterNumber) {
        super(username, name, phoneNumber, password, role, activated);

        if (operation != null) {
            this.id  = operation.getId();
            username = operation.getUsername();
            name = name == null ?operation.getName() : name;
            phoneNumber = phoneNumber == null ? operation.getPhoneNumber() : phoneNumber;
            password = password == null ? operation.getPassword() : password;
            businessType = businessType == null ? operation.getBusinessType() : businessType;
            businessName = businessName == null ? operation.getBusinessName() : businessName;
            businessRegisterNumber = businessRegisterNumber == null ? operation.getBusinessRegisterNumber() : businessRegisterNumber;
            activated = activated == false ? operation.isActivated() : activated;
            role = role == null ? operation.getRole() : role;
        }
        this.username = username;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.businessType = businessType;
        this.businessName = businessName;
        this.businessRegisterNumber = businessRegisterNumber;
        this.activated = activated;
        this.role = role;
    }

    public void update(Operation operation) {
        Operation.builder().operation(operation);
    }

    public static Operation registerOperation(UserDto.RegisterRequest registerRequest) {

        return Operation.builder()
                .username(registerRequest.getUsername())
                .name(registerRequest.getName())
                .password(registerRequest.getPassword())
                .phoneNumber(registerRequest.getPhoneNumber())
                .businessRegisterNumber(registerRequest.getBusinessRegisterNumber())
                .businessType(registerRequest.getBusinessType())
                .businessName(registerRequest.getBusinessName())
                .role(Role.valueOf(registerRequest.getRole().name()))
                .activated(true)
                .build();
    }

    public static Operation updateOperation(Operation operation, UserDto.UpdateRequest updateRequest) {

        return Operation.builder()
                .operation(operation)
                .name(updateRequest.getName())
                .password(updateRequest.getPassword())
                .phoneNumber(updateRequest.getPhoneNumber())
                .businessRegisterNumber(updateRequest.getBusinessRegisterNumber())
                .businessType(updateRequest.getBusinessType())
                .businessName(updateRequest.getBusinessName())
                .build();
    }
}
