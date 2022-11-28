package com.example.carefully.domain.user.entity;

import com.example.carefully.global.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.aop.framework.AdvisedSupportListener;

import javax.persistence.*;

import java.util.Set;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="User_Type")
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public abstract class CommonUser extends BaseEntity {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    @Column(name ="username", nullable = false, unique = true)
    String username;

    @Column(name = "name")
    String name;

    @Column(name ="phoneNumber", nullable = true)
    String phoneNumber;

    @Column(name ="password", nullable = false)
    String password;

    @Column(name = "activated")
    boolean activated;

    @Enumerated(value = STRING)
    Role role;

    public CommonUser(String username, String name, String phoneNumber, String password, Role role, boolean activated) {
        this.username = username;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.role = role;
        this.activated = activated;
    }
    
    public void signout() {
        this.activated = false; 
    }
}
