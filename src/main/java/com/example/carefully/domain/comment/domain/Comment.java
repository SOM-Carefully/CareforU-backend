package com.example.carefully.domain.comment.domain;

import com.example.carefully.domain.post.domain.Post;
import com.example.carefully.domain.user.entity.User;
import com.example.carefully.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

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
    private final List<Comment> child = new ArrayList<>();

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CommentStatus commentStatus;

    public enum CommentStatus {
        ALIVE, DELETED
    }

    @Builder
    public Comment(User user, Post post, String content,  Hierarchy hierarchy) {
        this.user = user;
        this.post = post;
        this.content = content;
        this.hierarchy = hierarchy;
        this.commentStatus = CommentStatus.ALIVE;
    }

    public void addParent(Comment parent) {
        this.parent = parent;
        if (parent != null) {
            parent.getChild().add(this);
        }
    }

    public boolean isParent() {
        return hierarchy == Hierarchy.PARENT;
    }

    /**
     * 자식이 없는 부모 댓글이거나 자식 댓글인지 확인한다.
     *
     * @return 댓글을 삭제할 수 있는 조건이면 참 반환
     */
    public boolean canDeleteComment() {
        return isParent() && child.size() == 0 || hierarchy == Hierarchy.CHILD;
    }

    public void changeStatusToDeleted() {
        this.commentStatus = CommentStatus.DELETED;
    }
}
