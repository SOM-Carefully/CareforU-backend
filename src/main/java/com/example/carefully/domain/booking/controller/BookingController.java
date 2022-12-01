package com.example.carefully.domain.booking.controller;

import com.example.carefully.domain.booking.dto.BookingDto;
import com.example.carefully.domain.booking.service.BookingService;
import com.example.carefully.global.dto.BaseResponse;
import com.example.carefully.global.entity.BaseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.carefully.domain.booking.dto.BookingResponseMessage.REQUEST_SUCCESS;

@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/service")
    public ResponseEntity requestService(@RequestBody BookingDto.ReceiveRequest receiveRequest) {
        bookingService.request(receiveRequest);
        return ResponseEntity.ok(BaseResponse.create(REQUEST_SUCCESS.getMessage()));
    }
}