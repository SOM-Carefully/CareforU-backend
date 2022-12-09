package com.example.carefully.domain.membership.controller;

import com.example.carefully.domain.membership.dto.MembershipDto;
import com.example.carefully.domain.membership.service.impl.MembershipServiceImpl;
import com.example.carefully.global.dto.BaseResponse;
import com.example.carefully.global.dto.SliceDto;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.example.carefully.domain.membership.dto.MembershipResponseMessage.LOOKUP_SUCCESS;
import static com.example.carefully.domain.membership.dto.MembershipResponseMessage.SIGNUP_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/membership")
@Api(tags = {"회원가입 신청 관련 API"})
public class MembershipController {
    private final MembershipServiceImpl membershipService;

    @GetMapping("")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<BaseResponse<SliceDto<MembershipDto.MembershipResponse>>> membershipLookup(@RequestParam("state") String membershipStatus,
                                                                                                     @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(BaseResponse.create(LOOKUP_SUCCESS.getMessage(), membershipService.membershipLookup(membershipStatus, pageable)));
    }

    @PutMapping("/accept/{membershipId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity acceptService(@PathVariable("membershipId") Long membershipId) {
        membershipService.accept(membershipId);
        return ResponseEntity.ok(BaseResponse.create(SIGNUP_SUCCESS.getMessage()));
    }

//    @PutMapping("/cancel/{membershipId}")
//    public ResponseEntity cancelService(@PathVariable("membershipId") Long membershipId) {
//        membershipService.reject(membershipId);
//        return ResponseEntity.ok(BaseResponse.create(CANCEL_SUCCESS.getMessage()));
//    }
}
