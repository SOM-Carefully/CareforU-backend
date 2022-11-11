package com.example.carefully.domain.user.service;

import com.example.carefully.domain.user.entity.CommonUser;
import com.example.carefully.domain.user.entity.Role;
import com.example.carefully.domain.user.repository.CommonUserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    private final CommonUserRepository commonUserRepository;

    public CustomUserDetailsService(CommonUserRepository commonUserRepository) {
        this.commonUserRepository = commonUserRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) {
        return commonUserRepository.findOneWithAuthoritiesByUsername(username)
                .map(commonUser -> createUser(username, commonUser))
                .orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    private org.springframework.security.core.userdetails.User createUser(String username, CommonUser commonUser) {
        if (!commonUser.isActivated()) {
            throw new RuntimeException(username + " -> 활성화되어 있지 않습니다.");
        }

        Role role = commonUser.getRole();

        List<GrantedAuthority> grantedAuthorities = List.of(new SimpleGrantedAuthority(role.getFullName()));

        return new org.springframework.security.core.userdetails.User(commonUser.getUsername(),
                commonUser.getPassword(),
                grantedAuthorities);
    }
}
