package com.example.carefully.domain.comment.domain;

import com.example.carefully.domain.post.domain.Post;
import com.example.carefully.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    @Enumerated(value = EnumType.STRING)
    private Hierarchy hierarchy;

    public enum Hierarchy {
        PARENT, CHILD
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Comment> child = new ArrayList<>();

    @Builder
    public Comment(Post post, String content, Comment parent, Hierarchy hierarchy) {
        this.post = post;
        this.content = content;
        this.parent = parent;
        this.hierarchy = hierarchy;
    }

    public Long getId() {
        return id;
    }
}
