package com.example.carefully.domain.user.service.impl;

import com.example.carefully.domain.user.entity.User;
import com.example.carefully.domain.user.entity.Role;
import com.example.carefully.domain.user.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component("userDetailsService")
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 유저 이메일로 유저 정보를 반환한다.
     * 해당되는 이메일의 유저가 없을시 error
     *
     * @param username 유저 이메일
     * @return 유저 ID, 유저 이메일, 이름, 주민번호, 휴대폰 번호, 성별, 국적, 대학교 이름, 전공, 지도교수 이름, 학위, 주소, 계정 활성화 여부, 등급 및 권한, 유저 프로필 정보
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) {
        return userRepository.findOneWithAuthoritiesByUsername(username)
                .map(commonUser -> createUser(username, commonUser))
                .orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    private org.springframework.security.core.userdetails.User createUser(String username, User user) {
        if (!user.isActivated()) {
            throw new RuntimeException(username + " -> 활성화되어 있지 않습니다.");
        }

        Role role = user.getRole();

        List<GrantedAuthority> grantedAuthorities = List.of(new SimpleGrantedAuthority(role.getFullName()));

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                grantedAuthorities);
    }
}
