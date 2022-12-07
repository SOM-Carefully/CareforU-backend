package com.example.carefully.domain.post.domain;

import com.example.carefully.global.entity.*;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;

    @Column(nullable = false)
    private Long userId;      // 임시 유저

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private String imgUrl;

    @Enumerated(value = EnumType.STRING)
    private PostRole postRole;

    private String locked;

    @Builder
    public Post(Long userId, String title, String content, boolean locked, String imgUrl, PostRole postRole) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.imgUrl = imgUrl;
        this.locked = mapString(locked);
        this.postRole = postRole;
    }

    public void updatePost(String title, String content, String imgUrl) {
        this.title = title;
        this.content = content;
        this.imgUrl = imgUrl;
    }

    public void updateQuest(String title, String content, boolean locked) {
        this.title = title;
        this.content = content;
        this.locked = mapString(locked);
    }

    private String mapString(boolean locked) {
        return locked ? "Y" : "N";
    }
}
