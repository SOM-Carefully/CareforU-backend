package com.example.carefully.global.utils;

import com.example.carefully.domain.user.entity.CommonUser;
import com.example.carefully.domain.user.exception.NotValidationRoleException;
import com.example.carefully.domain.user.repository.CommonUserRepository;
import com.example.carefully.global.security.util.SecurityUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserUtils {
    public static CommonUser getCurrentUser(CommonUserRepository commonUserRepository) {
        return SecurityUtil.getCurrentUsername()
                .flatMap(commonUserRepository::findOneWithAuthoritiesByUsername)
                .orElseThrow(() -> new NotValidationRoleException());
    }
}
