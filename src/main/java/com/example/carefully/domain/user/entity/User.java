package com.example.carefully.domain.user.entity;

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
    public User(String username, String name, String phoneNumber, String password, String foreignerNumber, Gender gender, String university, String major, Address address, boolean activated, Role role) {
        super(username, name, phoneNumber, password, role, activated);
        this.foreignerNumber = foreignerNumber;
        this.gender = gender;
        this.university = university;
        this.major = major;
        this.address = address;
    }
}