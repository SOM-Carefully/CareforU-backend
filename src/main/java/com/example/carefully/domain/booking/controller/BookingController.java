package com.example.carefully.domain.booking.controller;

import com.example.carefully.domain.booking.dto.BookingDto;
import com.example.carefully.domain.booking.service.Impl.BookingServiceImpl;
import com.example.carefully.global.dto.BaseResponse;
import com.example.carefully.global.dto.SliceDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.example.carefully.domain.booking.dto.BookingResponseMessage.*;

@RestController
@RequestMapping("/api/v1/services")
@Api(tags = {"서비스 신청 관련 API"})
@RequiredArgsConstructor
public class BookingController {
    private final BookingServiceImpl bookingService;

    @ApiOperation(value = "서비스 신청", notes = "서비스 신청 API")
    @PreAuthorize("hasAnyRole('CLASSIC','SILVER', 'GOLD', 'PLATINUM')")
    @PostMapping("")
    public ResponseEntity requestService(@RequestBody BookingDto.ReceiveRequest receiveRequest) {
        bookingService.request(receiveRequest);
        return ResponseEntity.ok(BaseResponse.create(REQUEST_SUCCESS.getMessage()));
    }

    @ApiOperation(value = "전체 서비스 신청 리스트 조회", notes = "서비스 조회 API")
    @GetMapping("/all")
    public ResponseEntity<BaseResponse<SliceDto<BookingDto.ServiceResponse>>> lookupAllService() {
        return ResponseEntity.ok(BaseResponse.create(LOOKUP_SUCCESS.getMessage(), bookingService.serviceAllLookup()));
    }

    @ApiOperation(value = "단일 서비스 조회", notes = "서비스 조회 API")
    @GetMapping("/{bookingId}")
    public ResponseEntity<BaseResponse<BookingDto.ServiceResponse>> lookupService(@PathVariable("bookingId") Long bookingId) {
        return ResponseEntity.ok(BaseResponse.create(LOOKUP_SUCCESS.getMessage(), bookingService.lookup(bookingId)));
    }

    @ApiOperation(value = "내 서비스 신청 리스트 조회", notes = "서비스 조회 API")
    @GetMapping("/my")
    public ResponseEntity<BaseResponse<SliceDto<BookingDto.ServiceResponse>>> lookupMyService() {
        return ResponseEntity.ok(BaseResponse.create(LOOKUP_SUCCESS.getMessage(), bookingService.userLookup()));
    }

    @ApiOperation(value = "내 서비스 수정", notes = "서비스 수정 API")
    @PutMapping("/update/{bookingId}")
    public ResponseEntity updateServiceContent(@PathVariable("bookingId") Long bookingId, @RequestBody BookingDto.UpdateRequest updateRequest) {
        bookingService.update(bookingId, updateRequest);
        return ResponseEntity.ok(BaseResponse.create(UPDATE_SUCCESS.getMessage()));
    }

    @ApiOperation(value = "서비스 승인", notes = "서비스 승인 API")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PatchMapping("/accept/{bookingId}")
    public ResponseEntity acceptService(@PathVariable("bookingId") Long bookingId) {
        bookingService.accept(bookingId);
        return ResponseEntity.ok(BaseResponse.create(ACCEPT_SUCCESS.getMessage()));
    }

    @ApiOperation(value = "서비스 취소", notes = "서비스 취소 API")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PatchMapping("/cancel/{bookingId}")
    public ResponseEntity cancelService(@PathVariable("bookingId") Long bookingId) {
        bookingService.cancel(bookingId);
        return ResponseEntity.ok(BaseResponse.create(CANCEL_SUCCESS.getMessage()));
    }

    @ApiOperation(value = "서비스 완료", notes = "서비스 완료 API")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PatchMapping("/complete/{bookingId}")
    public ResponseEntity completeService(@PathVariable("bookingId") Long bookingId) {
        bookingService.complete(bookingId);
        return ResponseEntity.ok(BaseResponse.create(COMPLETE_SUCCESS.getMessage()));
    }
}