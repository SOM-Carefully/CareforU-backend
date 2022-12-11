package com.example.carefully.global.utils;

import com.example.carefully.domain.user.entity.User;
import com.example.carefully.domain.user.exception.NotValidationRoleException;
import com.example.carefully.domain.user.repository.UserRepository;
import com.example.carefully.global.security.util.SecurityUtil;
import org.springframework.stereotype.Component;

@Component
public class UserUtils {
    public static User getCurrentUser(UserRepository userRepository) {
        return SecurityUtil.getCurrentUsername()
                .flatMap(userRepository::findOneWithAuthoritiesByUsername)
                .orElseThrow(NotValidationRoleException::new);
    }
}