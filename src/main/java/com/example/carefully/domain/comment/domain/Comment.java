package com.example.carefully.domain.comment.domain;

import com.example.carefully.domain.post.domain.Post;
import com.example.carefully.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;

    @Column(nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int hierarchy;

    @Column(nullable = false)
    private int group;

    @Builder
    public Comment(String content, int hierarchy, int group) {
        this.content = content;
        this.hierarchy = hierarchy;
        this.group = group;
    }
}
