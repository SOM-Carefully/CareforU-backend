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

    @Enumerated(value = STRING)
    Gender gender;

    @Column
    String nationality;

    @Column
    String universityName;

    @Column
    String major;

    @Column
    String advisorName;

    @Enumerated(value = STRING)
    Education education;

    @Embedded
    private Address address;

    @Column
    boolean activated;

    @Enumerated(value = STRING)
    Role role;

    @Builder
    public User(String username, String password, String name, String identificationNumber, String phoneNumber, Gender gender,
                String nationality, String universityName, String major, String advisorName, Education education,
                String address, Role role, boolean activated) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.identificationNumber = identificationNumber;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.nationality = nationality;
        this.universityName = universityName;
        this.major = major;
        this.advisorName = advisorName;
        this.education = education;
        this.address = new Address(address);
        this.role = role;
        this.activated = activated;
    }

    public static User userRequest(UserDto.UserRegisterRequest registerRequest) {
        return User.builder()
                .username(registerRequest.getUsername())
                .password(registerRequest.getPassword())
                .name(registerRequest.getName())
                .identificationNumber(registerRequest.getIdentificationNumber())
                .phoneNumber(registerRequest.getPhoneNumber())
                .gender(Gender.valueOf(registerRequest.getGenderRequest().name()))
                .nationality(registerRequest.getNationality())
                .universityName(registerRequest.getUniversityName())
                .major(registerRequest.getMajor())
                .advisorName(registerRequest.getAdvisorName())
                .education(Education.valueOf((registerRequest.getEducationRequest().name())))
                .address(registerRequest.getAddress())
                .activated(false)
                .role(Role.valueOf("CLASSIC"))
                .build();
    }

    public static User adminRequest(UserDto.AdminRegisterRequest registerRequest) {
        return User.builder()
                .username(registerRequest.getUsername())
                .password(registerRequest.getPassword())
                .name(registerRequest.getName())
                .phoneNumber(registerRequest.getPhoneNumber())
                .identificationNumber(registerRequest.getIdentificationNumber())
                .activated(false)
                .role(Role.valueOf("ADMIN"))
                .build();
    }

    public void updateUser(String name, String universityName, String education, String gender) {
        this.name = name;
        this.universityName = universityName;
        this.education = Education.valueOf(education);
        this.gender = Gender.valueOf(gender);
    }

    public void updateAdmin(String name, String gender) {
        this.name = name;
        this.gender = Gender.valueOf(gender);
    }

    public void updateUserRole(String role) {
        this.role = Role.valueOf(role);
    }

    public void signup() { this.activated = true; }

    public void signout() {
        this.activated = false; 
    }
}
