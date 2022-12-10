package com.example.carefully.domain.membership.entity;

import com.example.carefully.domain.user.entity.User;
import com.example.carefully.global.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.example.carefully.domain.membership.entity.MembershipStatus.ACCEPT;
import static com.example.carefully.domain.membership.entity.MembershipStatus.REJECT;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Membership extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private User admin;

    @Column
    String content;

    @Enumerated(value = STRING)
    MembershipStatus membershipStatus;

    @Builder
    public Membership(User user, String content, MembershipStatus membershipStatus) {
        this.user = user;
        this.content = content;
        this.membershipStatus = membershipStatus;
    }

    public static Membership request(User user, String content) {
        return Membership.builder()
                .user(user)
                .content(content)
                .membershipStatus(MembershipStatus.valueOf("WAITING"))
                .build();
    }

    public void setAdmin(User currentUser) {
        User admin = currentUser;
        this.admin = admin;
    }

    public void accept() {
        this.user.signup();
        this.membershipStatus = ACCEPT;
    }

    public void reject() { this.membershipStatus = REJECT; }

    public void setNullAdmin() { this.admin = null; }
}
