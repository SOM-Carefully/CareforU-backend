package com.example.carefully.domain.quest.domain;

import com.example.carefully.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quest extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quest_id")
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;

    @Column(nullable = false)
    private Long userId;

    private Long adminId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String locked;

    private String answer;

    @Builder
    public Quest(Long userId, String title, String content, boolean locked) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.locked = mapString(locked);
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
