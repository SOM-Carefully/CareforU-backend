package com.example.carefully.domain.booking.controller;

import com.example.carefully.domain.booking.dto.BookingDto;
import com.example.carefully.domain.booking.service.Impl.BookingServiceImpl;
import com.example.carefully.global.dto.BaseResponse;
import com.example.carefully.global.dto.SliceDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @ApiOperation(value = "교육 서비스 신청", notes = "교육 서비스를 신청합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "서비스 신청이 완료되었습니다."),
            @ApiResponse(responseCode = "500", description = "로그인이 안 되어 있거나 활성회되지 않은 회원의 경우 발생할 수 있습니다."),
            @ApiResponse(responseCode = "401", description = "권한이 없는 유저가 접근했을 경우 발생할 수 있습니다.")
    })
    @PreAuthorize("hasAnyRole('CLASSIC','SILVER', 'GOLD', 'PLATINUM')")
    @PostMapping("/educations")
    public ResponseEntity educationRequestService(@RequestBody BookingDto.EducationReceiveRequest receiveRequest) {
        bookingService.educationRequest(receiveRequest);
        return ResponseEntity.ok(BaseResponse.create(REQUEST_SUCCESS.getMessage()));
    }

    @ApiOperation(value = "교통 서비스 신청", notes = "교통 서비스를 신청합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "서비스 신청이 완료되었습니다."),
            @ApiResponse(responseCode = "500", description = "로그인이 안 되어 있거나 활성회되지 않은 회원의 경우 발생할 수 있습니다."),
            @ApiResponse(responseCode = "401", description = "권한이 없는 유저가 접근했을 경우 발생할 수 있습니다.")
    })
    @PreAuthorize("hasAnyRole('CLASSIC','SILVER', 'GOLD', 'PLATINUM')")
    @PostMapping("/traffics")
    public ResponseEntity trafficRequestService(@RequestBody BookingDto.TrafficReceiveRequest trafficReceiveRequest) {
        bookingService.trafficRequest(trafficReceiveRequest);
        return ResponseEntity.ok(BaseResponse.create(REQUEST_SUCCESS.getMessage()));
    }

    @ApiOperation(value = "주거 서비스 신청", notes = "주거 서비스를 신청합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "서비스 신청이 완료되었습니다."),
            @ApiResponse(responseCode = "500", description = "로그인이 안 되어 있거나 활성회되지 않은 회원의 경우 발생할 수 있습니다."),
            @ApiResponse(responseCode = "401", description = "권한이 없는 유저가 접근했을 경우 발생할 수 있습니다.")
    })
    @PreAuthorize("hasAnyRole('CLASSIC','SILVER', 'GOLD', 'PLATINUM')")
    @PostMapping("/dwellings")
    public ResponseEntity dwellingRequestService(@RequestBody BookingDto.DwellingReceiveRequest receiveRequest) {
        bookingService.dwellingRequest(receiveRequest);
        return ResponseEntity.ok(BaseResponse.create(REQUEST_SUCCESS.getMessage()));
    }

    @ApiOperation(value = "통신 서비스 신청", notes = "통신 서비스를 신청합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "서비스 신청이 완료되었습니다."),
            @ApiResponse(responseCode = "500", description = "로그인이 안 되어 있거나 활성회되지 않은 회원의 경우 발생할 수 있습니다."),
            @ApiResponse(responseCode = "401", description = "권한이 없는 유저가 접근했을 경우 발생할 수 있습니다.")
    })
    @PreAuthorize("hasAnyRole('CLASSIC','SILVER', 'GOLD', 'PLATINUM')")
    @PostMapping("/communications")
    public ResponseEntity communicationRequestService(@RequestBody BookingDto.CommunicationReceiveRequest communicationReceiveRequest) {
        bookingService.communicationRequest(communicationReceiveRequest);
        return ResponseEntity.ok(BaseResponse.create(REQUEST_SUCCESS.getMessage()));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "서비스 조회에 성공하였습니다."),
            @ApiResponse(responseCode = "500", description = "로그인이 안 되어 있거나 활성회되지 않은 회원의 경우 발생할 수 있습니다."),
            @ApiResponse(responseCode = "401", description = "권한이 없는 유저가 접근했을 경우 발생할 수 있습니다.")
    })
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ApiOperation(value = "전체 서비스 신청 리스트 조회", notes = "전체 서비스 신청 리스트를 조회합니다.")
    @GetMapping("/all")
    public ResponseEntity<BaseResponse<SliceDto<BookingDto.ServiceAllResponse>>> lookupAllService() {
        return ResponseEntity.ok(BaseResponse.create(LOOKUP_SUCCESS.getMessage(), bookingService.serviceAllLookup()));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "서비스 조회에 성공하였습니다."),
            @ApiResponse(responseCode = "500", description = "로그인이 안 되어 있거나 활성회되지 않은 회원의 경우 발생할 수 있습니다."),
            @ApiResponse(responseCode = "401", description = "권한이 없는 유저가 접근했을 경우 발생할 수 있습니다.")
    })
    @ApiOperation(value = "단일 서비스 상세 조회 (교육)", notes = "교육 서비스 신청 상세 내역을 조회합니다.")
    @GetMapping("/educations/{bookingId}")
    public ResponseEntity<BaseResponse<BookingDto.EducationReceiveResponse>> lookupEducationService(@PathVariable("bookingId") Long bookingId) {
        return ResponseEntity.ok(BaseResponse.create(LOOKUP_SUCCESS.getMessage(), bookingService.educationLookup(bookingId)));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "서비스 조회에 성공하였습니다."),
            @ApiResponse(responseCode = "500", description = "로그인이 안 되어 있거나 활성회되지 않은 회원의 경우 발생할 수 있습니다."),
            @ApiResponse(responseCode = "401", description = "권한이 없는 유저가 접근했을 경우 발생할 수 있습니다.")
    })
    @ApiOperation(value = "단일 서비스 상세 조회 (교통)", notes = "교통 서비스 신청 상세 내역을 조회합니다.")
    @GetMapping("/traffics/{bookingId}")
    public ResponseEntity<BaseResponse<BookingDto.TrafficReceiveResponse>> lookupTrafficService(@PathVariable("bookingId") Long bookingId) {
        return ResponseEntity.ok(BaseResponse.create(LOOKUP_SUCCESS.getMessage(), bookingService.trafficLookup(bookingId)));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "서비스 조회에 성공하였습니다."),
            @ApiResponse(responseCode = "500", description = "로그인이 안 되어 있거나 활성회되지 않은 회원의 경우 발생할 수 있습니다."),
            @ApiResponse(responseCode = "401", description = "권한이 없는 유저가 접근했을 경우 발생할 수 있습니다.")
    })
    @ApiOperation(value = "단일 서비스 상세 조회 (주거)", notes = "주거 서비스 신청 상세 내역을 조회합니다.")
    @GetMapping("/dwellings/{bookingId}")
    public ResponseEntity<BaseResponse<BookingDto.DwellingReceiveResponse>> lookupDwellingService(@PathVariable("bookingId") Long bookingId) {
        return ResponseEntity.ok(BaseResponse.create(LOOKUP_SUCCESS.getMessage(), bookingService.dwellingLookup(bookingId)));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "서비스 조회에 성공하였습니다."),
            @ApiResponse(responseCode = "500", description = "로그인이 안 되어 있거나 활성회되지 않은 회원의 경우 발생할 수 있습니다."),
            @ApiResponse(responseCode = "401", description = "권한이 없는 유저가 접근했을 경우 발생할 수 있습니다.")
    })
    @ApiOperation(value = "단일 서비스 상세 조회 (통신)", notes = "통신 서비스 신청 상세 내역을 조회합니다.")
    @GetMapping("/communications/{bookingId}")
    public ResponseEntity<BaseResponse<BookingDto.CommunicationReceiveResponse>> lookupCommunicationService(@PathVariable("bookingId") Long bookingId) {
        return ResponseEntity.ok(BaseResponse.create(LOOKUP_SUCCESS.getMessage(), bookingService.communicationLookup(bookingId)));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "서비스 조회에 성공하였습니다."),
            @ApiResponse(responseCode = "500", description = "로그인이 안 되어 있거나 활성회되지 않은 회원의 경우 발생할 수 있습니다."),
            @ApiResponse(responseCode = "401", description = "권한이 없는 유저가 접근했을 경우 발생할 수 있습니다.")
    })
    @ApiOperation(value = "내 서비스 신청 리스트 조회", notes = "일반 회원일 경우 내가 신청한 서비스 리스트를 반환하고, 어드민 회원일 경우 내가 승인한 서비스 리스트를 반환합니다.")
    @GetMapping("/my")
    public ResponseEntity<BaseResponse<SliceDto<BookingDto.ServiceAllResponse>>> lookupMyService() {
        return ResponseEntity.ok(BaseResponse.create(LOOKUP_SUCCESS.getMessage(), bookingService.userLookup()));
    }


    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "서비스 수락에 성공하였습니다."),
            @ApiResponse(responseCode = "500", description = "로그인이 안 되어 있거나 활성회되지 않은 회원의 경우 발생할 수 있습니다."),
            @ApiResponse(responseCode = "401", description = "권한이 없는 유저가 접근했을 경우 발생할 수 있습니다.")
    })
    @ApiOperation(value = "서비스 승인", notes = "서비스를 승인합니다.")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PatchMapping("/accept/{bookingId}")
    public ResponseEntity acceptService(@PathVariable("bookingId") Long bookingId, @RequestBody BookingDto.ServiceAcceptRequest serviceAcceptRequest) {
        bookingService.accept(bookingId, serviceAcceptRequest);
        return ResponseEntity.ok(BaseResponse.create(ACCEPT_SUCCESS.getMessage()));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "서비스 취소에 성공하였습니다."),
            @ApiResponse(responseCode = "500", description = "로그인이 안 되어 있거나 활성회되지 않은 회원의 경우 발생할 수 있습니다."),
            @ApiResponse(responseCode = "401", description = "권한이 없는 유저가 접근했을 경우 발생할 수 있습니다.")
    })
    @ApiOperation(value = "서비스 취소", notes = "서비스 취소 API")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PatchMapping("/cancel/{bookingId}")
    public ResponseEntity cancelService(@PathVariable("bookingId") Long bookingId, @RequestBody BookingDto.ServiceRejectRequest serviceRejectRequest) {
        bookingService.cancel(bookingId, serviceRejectRequest);
        return ResponseEntity.ok(BaseResponse.create(CANCEL_SUCCESS.getMessage()));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "서비스 완료에 성공하였습니다."),
            @ApiResponse(responseCode = "500", description = "로그인이 안 되어 있거나 활성회되지 않은 회원의 경우 발생할 수 있습니다."),
            @ApiResponse(responseCode = "401", description = "권한이 없는 유저가 접근했을 경우 발생할 수 있습니다.")
    })
    @ApiOperation(value = "서비스 완료", notes = "서비스 완료 API")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PatchMapping("/complete/{bookingId}")
    public ResponseEntity completeService(@PathVariable("bookingId") Long bookingId) {
        bookingService.complete(bookingId);
        return ResponseEntity.ok(BaseResponse.create(COMPLETE_SUCCESS.getMessage()));
    }
}