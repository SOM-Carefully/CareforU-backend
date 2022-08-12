package com.example.carefully.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name="users")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class User extends BaseEntity {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name ="username", nullable = false, unique = true)
    private String username;

    @Column(name ="phoneNumber", nullable = true)
    private String phoneNumber;

    @Column(name ="email", nullable = false)
    private String email;

    @Column(name ="password", nullable = false)
    private String password;

    @Column(name ="name", nullable = false)
    private String name;

    @Column(name ="gender")
    @Enumerated(value = STRING)
    private Gender gender;

    @Column(name ="university")
    private String university;

    @Embedded
    private Address address;

    @Column(name = "activated")
    private boolean activated;

    @Enumerated(value = STRING)
    private Role role;

    //== 비지니스 메서드 ==//
    public void updateInfo(String email, String name, Gender gender, String phoneNumber, String address, String university) {
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = new Address(address);
        this.university = university;
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}