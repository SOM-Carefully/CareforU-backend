package com.example.carefully.domain.user.entity;

import com.example.carefully.domain.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

@Entity
@DiscriminatorValue("General")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class General extends User {

    @Column(name="foreignerNumber")
    private String foreignerNumber;

    @Column(name ="gender")
    @Enumerated(value = STRING)
    private Gender gender;

    @Column(name ="university")
    private String university;

    @Column(name="major")
    private String major;

    @Embedded
    private Address address;

    @Builder
    public General(General general, String username, String name, String phoneNumber, String password, String foreignerNumber, Gender gender, String university, String major, Address address, boolean activated, Role role) {
        super(username, name, phoneNumber, password, role, activated);

        if (general != null) {
            this.id = general.getId();
            username = username == null ? general.getUsername() : username;
            name = name == null ? general.getName() : name;
            phoneNumber = phoneNumber == null ? general.getPhoneNumber() : phoneNumber;
            password = password == null ? general.getPassword() : password;
            foreignerNumber = foreignerNumber == null ? general.getForeignerNumber() : foreignerNumber;
            gender = gender == null ? general.getGender() : gender;
            university = university == null ? general.getUniversity() : university;
            major = major == null ? general.getMajor() : major;
            address = address == null ? general.getAddress() : address;
            activated = activated == false ? general.isActivated() : activated;
            role = role == null ? general.getRole() : role;
        }
        this.username = username;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.foreignerNumber = foreignerNumber;
        this.gender = gender;
        this.university = university;
        this.major = major;
        this.address = address;
        this.activated = activated;
        this.role = role;
    }

    public void update(General general) {
        General.builder().general(general);
    }

    public static General registerUser(UserDto.RegisterRequest registerRequest) {
        return General.builder()
                .username(registerRequest.getUsername())
                .password(registerRequest.getPassword())
                .name(registerRequest.getName())
                .phoneNumber(registerRequest.getPhoneNumber())
                .foreignerNumber(registerRequest.getForeignerNumber())
                .gender(registerRequest.getGender())
                .address(registerRequest.getAddress())
                .university(registerRequest.getUniversity())
                .major(registerRequest.getMajor())
                .role(Role.valueOf(registerRequest.getRole().name()))
                .activated(true)
                .build();
    }

    public static General updateUser(General general, UserDto.UpdateRequest updateRequest) {
        return General.builder()
                .general(general)
                .password(updateRequest.getPassword())
                .name(updateRequest.getName())
                .phoneNumber(updateRequest.getPhoneNumber())
                .foreignerNumber(updateRequest.getForeignerNumber())
                .gender(updateRequest.getGender())
                .address(updateRequest.getAddress())
                .university(updateRequest.getUniversity())
                .major(updateRequest.getMajor())
                .build();
    }
}