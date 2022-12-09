package com.example.carefully.domain.user.entity;

import com.example.carefully.domain.user.dto.UserDto;
import com.example.carefully.global.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@AllArgsConstructor
@Table(name = "user_table")
@NoArgsConstructor(access = PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    @Column(nullable = false, unique = true)
    String username;

    @Column(nullable = false)
    String password;
    @Column(nullable = false)
    String name;

    @Column(unique = true)
    String identificationNumber;

    @Column(nullable = false, unique = true)
    String phoneNumber;
    @Column(unique = true)
    String businessRegistrationNumber;

    @Column
    String universityName;

    @Enumerated(value = STRING)
    Education education;

    @Enumerated(value = STRING)
    Gender gender;

    @Column
    boolean activated;

    @Enumerated(value = STRING)
    Role role;

    @Builder
    public User(String username, String password, String name, String phoneNumber, String identificationNumber,
                String businessRegistrationNumber, String universityName, Education education, Role role,
                Gender gender, boolean activated) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.identificationNumber = identificationNumber;
        this.businessRegistrationNumber = businessRegistrationNumber;
        this.universityName = universityName;
        this.education = education;
        this.gender = gender;
        this.role = role;
        this.activated = activated;
    }

    public static User userRequest(UserDto.UserRegisterRequest registerRequest) {
        return User.builder()
                .username(registerRequest.getUsername())
                .password(registerRequest.getPassword())
                .name(registerRequest.getName())
                .phoneNumber(registerRequest.getPhoneNumber())
                .universityName(registerRequest.getUniversityName())
                .education(Education.valueOf((registerRequest.getEducationRequest().name())))
                .activated(false)
                .role(Role.valueOf((registerRequest.getRole().name())))
                .build();
    }

    public static User adminRequest(UserDto.AdminRegisterRequest registerRequest) {
        return User.builder()
                .username(registerRequest.getUsername())
                .password(registerRequest.getPassword())
                .name(registerRequest.getName())
                .phoneNumber(registerRequest.getPhoneNumber())
                .businessRegistrationNumber(registerRequest.getBusinessRegistrationNumber())
                .activated(false)
                .role(Role.valueOf("ADMIN"))
                .build();
    }

    public void signup() { this.activated = true; }

    public void signout() {
        this.activated = false; 
    }
}
