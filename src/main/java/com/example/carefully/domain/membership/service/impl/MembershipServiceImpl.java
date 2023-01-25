package com.example.carefully.domain.membership.service.impl;

import com.example.carefully.domain.booking.entity.Booking;
import com.example.carefully.domain.membership.dto.MembershipDto;
import com.example.carefully.domain.membership.entity.Membership;
import com.example.carefully.domain.membership.entity.MembershipStatus;
import com.example.carefully.domain.membership.exception.AlreadyProcessedMembership;
import com.example.carefully.domain.membership.exception.NotValidationMembershipAdmin;
import com.example.carefully.domain.membership.exception.NotValidationMembershipUser;
import com.example.carefully.domain.membership.repository.MembershipRepository;
import com.example.carefully.domain.membership.service.MembershipService;
import com.example.carefully.domain.user.entity.User;
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
    private final MembershipRepository membershipRepository;
    private final UserRepository userRepository;

    /*
    회원가입 신청 전체 리스트 조회
    */
    @Override
    @Transactional(readOnly = true)
    public SliceDto<MembershipDto.MembershipResponse> membershipAllLookup() {
        Slice<Membership> membershipList = membershipRepository.findAllByOrderByCreatedAtDesc();
        return SliceDto.create(membershipList.map(MembershipDto.MembershipResponse::create));
    }


    /*
    회원가입 신청 상태별 리스트 조회
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
        User currentUser = getCurrentUser(userRepository);
        Membership membership = membershipRepository.getReferenceById(membershipId);
        checkMembershipRequestUserAndRoleAdmin(membership, currentUser);
        return MembershipDto.MembershipResponse.create(membership);
    }

    /*
    회원가입 승인
     */
    @Override
    @Transactional
    public void accept(Long membershipId) {
        Membership membership = membershipRepository.findById(membershipId).orElseThrow();
        User currentUser = getCurrentUser(userRepository);

        membership = checkAcceptAdmin(membership, currentUser);
        membership.accept();
        membershipRepository.save(membership);
    }

    @Override
    @Transactional
    public void reject(Long membershipId) {
        Membership membership = membershipRepository.findById(membershipId).orElseThrow();
        User currentUser = getCurrentUser(userRepository);

        membership = checkAcceptAdmin(membership, currentUser);
        membership.reject();
        membershipRepository.save(membership);
    }

    public boolean checkCurrentAdmin(Membership membership, User currentUser) {
        if (membership.getAdmin() == currentUser) {
            return true;
        } else {
            throw new NotValidationMembershipAdmin();
        }
    }

    public Membership checkAcceptAdmin(Membership membership, User user) {
        if (membership.getAdmin() == null) {
            membership.setAdmin(user);
        } else if (checkCurrentAdmin(membership, user)) {
            membership.setNullAdmin();
            checkAcceptAdmin(membership, user);
        }
        return membership;
    }

    public void checkMembershipRequestUserAndRoleAdmin(Membership membership, User currentUser) {
        if (currentUser.getRole().getFullName().equals("ROLE_ADMIN") || membership.getUser().getUsername().equals(currentUser.getUsername())) {
        } else {
            throw new NotValidationMembershipUser();
        }
    }
}
