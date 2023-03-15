package com.example.carefully.domain.category.domain;

import com.example.carefully.domain.post.domain.Post;
import com.example.carefully.domain.user.entity.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private final List<Post> postList = new ArrayList<>();

    private String name;

    public Category(String name) {
        this.name = name;
    }

    public void updateName(String name) {
        this.name = name;
    }

    /**
     * 등급에 관련된 카테고리가 아닌 경우 접근 허용하도록 해당 메서드를 통해 검증한다. EX) 비밀/만남/토론
     *
     * @return 등급 카테고리인 경우 참, 등급이 아닌 카테고리인 경우 거짓 반환
     */
    public boolean isAssociatedToRank() {
        try {
            Role categoryRole = Role.of(name);
            return categoryRole.isPaidRole();
        } catch (NoSuchElementException ex) {
            log.info("categoryId={} name={} 유료 회원 카테고리가 아닌 자유 카테고리 요청입니다.", id, name);
            return false;
        }
    }

    /**
     * 현재 로그인한 유저의 등급과 카테고리 등급이 같다면 접근 허용하도록 해당 메서드를 통해 검증한다.
     *
     * @param role 현재 로그인 한 유저의 역할
     * @return 해당 카테고리가 현재 로그인한 유저의 등급과 같다면 참 반환
     */
    public boolean isSameRankWithUser(Role role) {
        return name.equals(role.getDescription());
    }

    /**
     * 1등급 카테고리의 경우만 모든 접근을 허용하도록 해당 메서드를 통해 검증한다.
     *
     * @return 해당 카테고리가 1등급인 경우 참 반환
     */
    public boolean isClassic() {
        return name.equals(Role.LEVEL1.getDescription());
    }
}
