package com.example.carefully.domain.user.repository;

import com.example.carefully.domain.booking.entity.Booking;
import com.example.carefully.domain.user.entity.User;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    User findByUsername(String username);
    Optional<User> findOneWithAuthoritiesByUsername(String username);

    // 활성화된 회원 모두 조회
    Slice<User> findAllByActivatedTrueOrderByCreatedAtDesc();
}