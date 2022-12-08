package com.example.carefully.domain.membership.entity;

import com.example.carefully.domain.booking.dto.BookingDto;
import com.example.carefully.domain.booking.entity.Booking;
import com.example.carefully.domain.booking.entity.BookingStatus;
import com.example.carefully.domain.user.dto.UserDto;
import com.example.carefully.domain.user.entity.User;
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
        this.content = content;
        this.membershipStatus = membershipStatus;
    }

    public static Membership request(User user, String content) {
        return Membership.builder()
                .user(user)
                .content(content)
                .build();
    }


}
