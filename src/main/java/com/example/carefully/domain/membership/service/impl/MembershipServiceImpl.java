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

    /**
     * 전체 회원가입 신청 내역 리스트를 조회한다.
     *
     * @return 회원가입 신청 ID, 신청일시, 신청 상태(ACCEPT/WAITING/REJECT), 회원가입 신청한 유저 이메일, 유저 등급(LEVEL1/LEVEL2/LEVEL3/LEVEL4/LEVEL5/ADMIN), 회원가입 신청을 처리한 어드민 이메일, 회원가입시 작성한 내용
     */
    @Override
    @Transactional(readOnly = true)
    public SliceDto<MembershipDto.MembershipResponse> membershipAllLookup() {
        Slice<Membership> membershipList = membershipRepository.findAllByOrderByCreatedAtDesc();
        return SliceDto.create(membershipList.map(MembershipDto.MembershipResponse::create));
    }


    /**
     * 회원가입 신청 상태별 회원 가입 신청 내역 리스트를 조회한다.
     *
     * @param membershipStatus 회원가입 신청 상태, pageable 페이지 정보
     * @return MembershipResponse 정보 및 페이지 정보가 포함된 리스트 - 회원가입 신청 고유 번호, 회원가입 신청일시, 회원가입 신청 상태, 회원가입 신청한 유저 이메일, 회원가입 신청 등급, 회원가입 신청을 처리한 어드민 이메일, 회원가입시 작성한 내용
     */
    @Override
    @Transactional(readOnly = true)
    public SliceDto<MembershipDto.MembershipResponse> membershipLookup(String membershipStatus, Pageable pageable) {
        Slice<Membership> membershipList = membershipRepository.findAllByMembershipStatusOrderByCreatedAtDesc(pageable, MembershipStatus.valueOf(membershipStatus));
        return SliceDto.create(membershipList.map(MembershipDto.MembershipResponse::create));
    }

    /**
     * 회원가입 신청 내역을 상세 조회한다.
     * 회원가입 신청한 유저와 어드민만 가능하다.
     *
     * @param membershipId 회원가입 신청 ID
     * @return MembershipResponse 정보 및 페이지 정보가 포함된 리스트 - 회원가입 신청 고유 번호, 회원가입 신청일시, 회원가입 신청 상태, 회원가입 신청한 유저 이메일, 회원가입 신청 등급, 회원가입 신청을 처리한 어드민 이메일, 회원가입시 작성한 내용
     */
    @Override
    @Transactional(readOnly = true)
    public MembershipDto.MembershipResponse lookup(Long membershipId) {
        User currentUser = getCurrentUser(userRepository);
        Membership membership = membershipRepository.getReferenceById(membershipId);
        checkMembershipRequestUserAndRoleAdmin(membership, currentUser);
        return MembershipDto.MembershipResponse.create(membership);
    }

    /**
     * 어드민 회원이 회원가입 신청을 승인한다.
     *
     * @param membershipId 회원가입 신청 ID
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

    /**
     * 어드민 회원이 회원가입 신청을 거절한다.
     *
     * @param membershipId 회원가입 신청 ID
     */
    @Override
    @Transactional
    public void reject(Long membershipId) {
        Membership membership = membershipRepository.findById(membershipId).orElseThrow();
        User currentUser = getCurrentUser(userRepository);

        membership = checkAcceptAdmin(membership, currentUser);
        membership.reject();
        membershipRepository.save(membership);
    }

    /**
     * 회언가입 신청 상태 수정시 기존에 처리한 어드민 회원과 동일한지 확인한다.
     *
     * @param membership 회원가입 신청 정보, currentUser 회원가입 상태를 수정하려고 하는 유저
     * @return 기존에 회원가입 신청을 처리한 유저와 현재 유저가 같다면 true, 아니라면 error 반환
     */
    public boolean checkCurrentAdmin(Membership membership, User currentUser) {
        if (membership.getAdmin() == currentUser) {
            return true;
        } else {
            throw new NotValidationMembershipAdmin();
        }
    }

    /**
     * 회원가입 신청을 처리한 어드민이 널일 경우 해당 서비스를 처리한 어드민을 현재 유저로 설정한다.
     * 회원가입 신청을 처리한 어드민과 동일한 유저일 경우 처리한 어드민을 Null로 변경한다.
     * 회원가입 신청 상태 변경시 checkAcceptAdmin를 호출하여 다시 현재 유저로 해당 회원가입 신청을 처리한 어드민을 현재 유저로 설정하기 위함.
     *
     * @param membership 처리할 회원가입 신청 정보, user 현재 유저
     * @return membership 처리할 회원가입 신청 정보
     */
    public Membership checkAcceptAdmin(Membership membership, User user) {
        if (membership.getAdmin() == null) {
            membership.setAdmin(user);
        } else if (checkCurrentAdmin(membership, user)) {
            membership.setNullAdmin();
            checkAcceptAdmin(membership, user);
        }
        return membership;
    }

    /**
     * 접근하려는 유저가 어드민이거나 회원가입 신청한 유저인지 확인한다.
     *
     * @param membership 처리할 회원가입 신청 정보, user 현재 유저
     * @return 접근하려는 유저가 어드민이거나 회원가입 신청한 유저가 아닐 경우 error
     */
    public void checkMembershipRequestUserAndRoleAdmin(Membership membership, User currentUser) {
        if (currentUser.getRole().getFullName().equals("ROLE_ADMIN") || membership.getUser().getUsername().equals(currentUser.getUsername())) {
        } else {
            throw new NotValidationMembershipUser();
        }
    }
}
