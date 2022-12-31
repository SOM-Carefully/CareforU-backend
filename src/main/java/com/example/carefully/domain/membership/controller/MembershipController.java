package com.example.carefully.domain.membership.controller;

import com.example.carefully.domain.membership.dto.MembershipDto;
import com.example.carefully.domain.membership.service.impl.MembershipServiceImpl;
import com.example.carefully.global.dto.BaseResponse;
import com.example.carefully.global.dto.SliceDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.example.carefully.domain.membership.dto.MembershipResponseMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/memberships")
@Api(tags = {"회원가입 신청 관련 API"})
public class MembershipController {
    private final MembershipServiceImpl membershipService;

    @GetMapping("/all")
    @ApiOperation(value = "회원가입 신청 전체 리스트 조회", notes = "회원가입 조회 API - 어드민 회원만 가능")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<BaseResponse<SliceDto<MembershipDto.MembershipResponse>>> membershipAllookup() {
        return ResponseEntity.ok(BaseResponse.create(LOOKUP_SUCCESS.getMessage(), membershipService.membershipAllLookup()));
    }

    @GetMapping
    @ApiOperation(value = "회원가입 신청 상태별 리스트 조회", notes = "회원가입 조회 API - 어드민 회원만 가능")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<BaseResponse<SliceDto<MembershipDto.MembershipResponse>>> membershipLookup(@RequestParam("state") String membershipStatus,
                                                                                                     @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(BaseResponse.create(LOOKUP_SUCCESS.getMessage(), membershipService.membershipLookup(membershipStatus, pageable)));
    }

    @PatchMapping("/accept/{membershipId}")
    @ApiOperation(value = "회원가입 신청 승인", notes = "회원가입 승인 API - 어드민 회원만 가능")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity acceptMembership(@PathVariable("membershipId") Long membershipId) {
        membershipService.accept(membershipId);
        return ResponseEntity.ok(BaseResponse.create(SIGNUP_SUCCESS.getMessage()));
    }

    @PatchMapping("/reject/{membershipId}")
    @ApiOperation(value = "회원가입 신청 거절", notes = "회원가입 거절 API - 어드민 회원만 가능")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity rejectMembership(@PathVariable("membershipId") Long membershipId) {
        membershipService.reject(membershipId);
        return ResponseEntity.ok(BaseResponse.create(SIGNUP_REJECT_SUCCESS.getMessage()));
    }
}
