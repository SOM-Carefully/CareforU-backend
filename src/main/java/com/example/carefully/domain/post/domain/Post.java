package com.example.carefully.domain.post.domain;

import com.example.carefully.domain.user.entity.BaseEntity;
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

    @Enumerated(value = EnumType.STRING)
    private PostRole postRole;

    @Builder
    public Post(Long userId, String title, String content, PostRole postRole){
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.postRole = postRole;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}
