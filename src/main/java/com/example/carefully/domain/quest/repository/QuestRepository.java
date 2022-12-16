package com.example.carefully.domain.quest.repository;

import com.example.carefully.domain.quest.domain.Quest;
import com.example.carefully.domain.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestRepository extends JpaRepository<Quest, Long> {
    Optional<Quest> findByIdAndUser(Long questId, User user);
    Slice<Quest> findAllByOrderByCreatedAtDesc(Pageable pageable);
    Slice<Quest> findAllByUserIdOrderByCreatedAtDesc(Pageable pageable, Long userId);
}
