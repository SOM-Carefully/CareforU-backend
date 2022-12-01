package com.example.carefully.domain.booking.controller;

import com.example.carefully.domain.booking.dto.BookingDto;
import com.example.carefully.domain.booking.service.Impl.BookingServiceImpl;
import com.example.carefully.global.dto.BaseResponse;
import com.example.carefully.global.dto.SliceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.carefully.domain.booking.dto.BookingResponseMessage.LOOKUP_SUCCESS;
import static com.example.carefully.domain.booking.dto.BookingResponseMessage.REQUEST_SUCCESS;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BookingController {
    private final BookingServiceImpl bookingService;

    @PostMapping("/service")
    public ResponseEntity requestService(@RequestBody BookingDto.ReceiveRequest receiveRequest) {
        bookingService.request(receiveRequest);
        return ResponseEntity.ok(BaseResponse.create(REQUEST_SUCCESS.getMessage()));
    }

    @GetMapping("/service/{bookingId}")
    public ResponseEntity<BaseResponse<BookingDto.ServiceResponse>> lookupService(@PathVariable("bookingId") Long bookingId) {
        return ResponseEntity.ok(BaseResponse.create(LOOKUP_SUCCESS.getMessage(), bookingService.lookup(bookingId)));
    }

    @GetMapping("/service/my")
    public ResponseEntity<BaseResponse<SliceDto<BookingDto.ServiceResponse>>> lookupMyService() {
        return ResponseEntity.ok(BaseResponse.create(LOOKUP_SUCCESS.getMessage(), bookingService.userLookup()));
    }
}