package com.example.carefully.domain.membership.service.impl;

import com.example.carefully.domain.membership.repository.MembershipRepository;
import com.example.carefully.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MembershipServiceImpl {
    private final UserRepository userRepository;
    private final MembershipRepository membershipRepository;


}
