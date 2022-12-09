package com.example.carefully.domain.membership.repository;

import com.example.carefully.domain.membership.entity.Membership;
import com.example.carefully.domain.membership.entity.MembershipStatus;
import com.example.carefully.domain.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
    Slice<Membership> findAllByMembershipStatusOrderByCreatedAtDesc(Pageable pageable, MembershipStatus membershipStatus);
    Slice<Membership> findAllByOrderByCreatedAtDesc();
    Slice<Membership> findAllByUser(User user);
}