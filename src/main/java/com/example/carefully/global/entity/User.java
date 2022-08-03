package com.example.carefully.global.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Set;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name="users")
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
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

    @Column(name = "activated")
    private boolean activated;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;

    @Builder
    public User(Long id, String username, String password, String name,
                Gender gender, String email, String phoneNumber,
                boolean activated, Set<Authority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.activated = activated;
        this.authorities = authorities;
    }

    //== 비지니스 메서드 ==//
    public void updateInfo(String email, String name, Gender gender, String phoneNumber) {
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}