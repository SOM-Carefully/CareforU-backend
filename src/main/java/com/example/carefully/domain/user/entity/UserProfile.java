package com.example.carefully.domain.user.entity;

import com.example.carefully.global.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class UserProfile extends BaseEntity  {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_profile_id")
    private Long id;

    private String profileUrl;

    private String nickname;

    private String bio;

    public void setProfile(String profileUrl, String nickname, String bio) {
        this.profileUrl = profileUrl;
        this.nickname = nickname;
        this.bio = bio;
    }
}
