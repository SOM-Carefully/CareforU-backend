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
@DiscriminatorValue("User")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class User extends CommonUser {

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
    public User(User user, String username, String name, String phoneNumber, String password, String foreignerNumber, Gender gender, String university, String major, Address address, boolean activated, Role role) {
        super(username, name, phoneNumber, password, role, activated);

        if (user != null) {
            this.id = user.getId();
            username = username == null ? user.getUsername() : username;
            name = name == null ? user.getName() : name;
            phoneNumber = phoneNumber == null ? user.getPhoneNumber() : phoneNumber;
            password = password == null ? user.getPassword() : password;
            foreignerNumber = foreignerNumber == null ? user.getForeignerNumber() : foreignerNumber;
            gender = gender == null ? user.getGender() : gender;
            university = university == null ? user.getUniversity() : university;
            major = major == null ? user.getMajor() : major;
            address = address == null ? user.getAddress() : address;
            activated = activated == false ? user.isActivated() : activated;
            role = role == null ? user.getRole() : role;
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

    public void update(User user) {
        User.builder().user(user);
    }

    public static User registerUser(UserDto.RegisterRequest registerRequest) {
        return User.builder()
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

    public static User updateUser(User user, UserDto.UpdateRequest updateRequest) {
        return User.builder()
                .user(user)
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