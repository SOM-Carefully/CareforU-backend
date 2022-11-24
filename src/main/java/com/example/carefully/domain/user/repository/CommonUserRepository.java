package com.example.carefully.domain.user.repository;

import com.example.carefully.domain.user.dto.UserDto;
import com.example.carefully.domain.user.entity.CommonUser;
import com.example.carefully.domain.user.entity.Operation;
import com.example.carefully.domain.user.entity.Role;
import com.example.carefully.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommonUserRepository extends JpaRepository<CommonUser, Long> {

    boolean existsByUsername(String username);
    Optional<CommonUser> findOneWithAuthoritiesByUsername(String username);
}