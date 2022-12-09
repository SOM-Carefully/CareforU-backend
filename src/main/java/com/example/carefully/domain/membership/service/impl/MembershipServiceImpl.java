package com.example.carefully.domain.membership.service.impl;

import com.example.carefully.domain.membership.dto.MembershipDto;
import com.example.carefully.domain.membership.entity.Membership;
import com.example.carefully.domain.membership.entity.MembershipStatus;
import com.example.carefully.domain.membership.exception.AlreadyProcessedMembership;
import com.example.carefully.domain.membership.exception.NotValidationMembershipAdmin;
import com.example.carefully.domain.membership.repository.MembershipRepository;
import com.example.carefully.domain.membership.service.MembershipService;
import com.example.carefully.domain.user.repository.UserRepository;
import com.example.carefully.global.dto.SliceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.carefully.global.utils.UserUtils.getCurrentUser;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MembershipServiceImpl implements MembershipService {
    private final UserRepository userRepository;
    private final MembershipRepository membershipRepository;


    /*
    회원가입 신청 리스트 조회
    */
    @Override
    @Transactional(readOnly = true)
    public SliceDto<MembershipDto.MembershipResponse> membershipLookup(String membershipStatus, Pageable pageable) {
        Slice<Membership> membershipList = membershipRepository.findAllByMembershipStatusOrderByCreatedAtDesc(pageable, MembershipStatus.valueOf(membershipStatus));
        return SliceDto.create(membershipList.map(MembershipDto.MembershipResponse::create));
    }

    /*
    단일 회원가입 신청 조회
     */
    @Override
    @Transactional(readOnly = true)
    public MembershipDto.MembershipResponse lookup(Long membershipId) {
        Membership membership = membershipRepository.getReferenceById(membershipId);
        return MembershipDto.MembershipResponse.create(membership);
    }

    /*
    회원가입 승인
     */
    @Override
    @Transactional
    public void accept(Long membershipId) {
     Membership membership = membershipRepository.findById(membershipId).orElseThrow();
     if (membership.getAdmin() == null) {
         membership.setAdmin(userRepository);
         membership.accept();
         membershipRepository.save(membership);
     } else if (checkCurrentAdmin(membership)) {
         accept(membershipId);
     } else {
         throw new AlreadyProcessedMembership();
     }
    }

    @Override
    @Transactional
    public void reject(Long membershipId) {
        Membership membership = membershipRepository.findById(membershipId).orElseThrow();
        if (membership.getAdmin() == null) {
            membership.setAdmin(userRepository);
            membership.reject();
            membershipRepository.save(membership);
        } else if (checkCurrentAdmin(membership)) {
            reject(membershipId);
        } else {
            throw new AlreadyProcessedMembership();
        }
    }

    public boolean checkCurrentAdmin(Membership membership) {
        if (membership.getAdmin() == getCurrentUser(userRepository)) {
            membership.setNullAdmin();
            return true;
        } else {
            throw new NotValidationMembershipAdmin();
        }
    }
}
