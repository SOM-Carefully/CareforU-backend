package com.example.carefully.domain.quest.repository;

import com.example.carefully.domain.quest.domain.Quest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestRepository extends JpaRepository<Quest, Long> {
    Slice<Quest> findAllByOrderByCreatedAtDesc(Pageable pageable);
    Slice<Quest> findAllByUserIdOrderByCreatedAtDesc(Pageable pageable, Long userId);
}
