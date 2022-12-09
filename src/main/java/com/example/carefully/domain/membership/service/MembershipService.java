package com.example.carefully.domain.membership.service;

import com.example.carefully.domain.membership.dto.MembershipDto;
import com.example.carefully.global.dto.SliceDto;
import org.springframework.data.domain.Pageable;

public interface MembershipService {
    SliceDto<MembershipDto.MembershipResponse> membershipAllLookup();
    SliceDto<MembershipDto.MembershipResponse> membershipLookup(String membershipStatus, Pageable pageable);
    MembershipDto.MembershipResponse lookup(Long membershipId);
    void accept(Long membershipId);
    void reject(Long membershipId);
}
