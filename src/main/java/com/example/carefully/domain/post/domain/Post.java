package com.example.carefully.domain.post.domain;

import com.example.carefully.domain.category.domain.Category;
import com.example.carefully.domain.user.entity.User;
import com.example.carefully.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private String imgUrl;

    @Enumerated(value = EnumType.STRING)
    private PostRole postRole;

    @Builder
    public Post(User user, Category category, String title, String content,
                String imgUrl, PostRole postRole) {
        this.user = user;
        this.category = category;
        this.title = title;
        this.content = content;
        this.imgUrl = imgUrl;
        this.postRole = postRole;
    }

    public void updatePost(String title, String content, String imgUrl) {
        this.title = title;
        this.content = content;
        this.imgUrl = imgUrl;
    }
}
