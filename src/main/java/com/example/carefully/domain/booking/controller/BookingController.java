package com.example.carefully.domain.booking.controller;

import com.example.carefully.domain.booking.dto.BookingDto;
import com.example.carefully.domain.booking.service.Impl.BookingServiceImpl;
import com.example.carefully.global.dto.BaseResponse;
import com.example.carefully.global.dto.SliceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.carefully.domain.booking.dto.BookingResponseMessage.*;

@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
public class BookingController {
    private final BookingServiceImpl bookingService;

    @PostMapping("")
    public ResponseEntity requestService(@RequestBody BookingDto.ReceiveRequest receiveRequest) {
        bookingService.request(receiveRequest);
        return ResponseEntity.ok(BaseResponse.create(REQUEST_SUCCESS.getMessage()));
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BaseResponse<BookingDto.ServiceResponse>> lookupService(@PathVariable("bookingId") Long bookingId) {
        return ResponseEntity.ok(BaseResponse.create(LOOKUP_SUCCESS.getMessage(), bookingService.lookup(bookingId)));
    }

    @GetMapping("")
    public ResponseEntity<BaseResponse<SliceDto<BookingDto.ServiceResponse>>> lookupMyService() {
        return ResponseEntity.ok(BaseResponse.create(LOOKUP_SUCCESS.getMessage(), bookingService.userLookup()));
    }

    @PutMapping("/update/{bookingId}")
    public ResponseEntity updateServiceContent(@PathVariable("bookingId") Long bookingId, @RequestBody BookingDto.UpdateRequest updateRequest) {
        bookingService.update(bookingId, updateRequest);
        return ResponseEntity.ok(BaseResponse.create(UPDATE_SUCCESS.getMessage()));
    }

    @PutMapping("/accept/{bookingId}")
    public ResponseEntity acceptService(@PathVariable("bookingId") Long bookingId) {
        bookingService.accept(bookingId);
        return ResponseEntity.ok(BaseResponse.create(ACCEPT_SUCCESS.getMessage()));
    }

    @PutMapping("/cancel/{bookingId}")
    public ResponseEntity cancelService(@PathVariable("bookingId") Long bookingId) {
        bookingService.cancel(bookingId);
        return ResponseEntity.ok(BaseResponse.create(CANCEL_SUCCESS.getMessage()));
    }

    @PutMapping("/complete/{bookingId}")
    public ResponseEntity completeService(@PathVariable("bookingId") Long bookingId) {
        bookingService.complete(bookingId);
        return ResponseEntity.ok(BaseResponse.create(COMPLETE_SUCCESS.getMessage()));
    }
}