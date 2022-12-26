package com.example.carefully.domain.booking.dto;

import com.example.carefully.domain.booking.entity.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;

public class BookingDto {
    @Getter
    @RequiredArgsConstructor
    public enum StateRequest {
        USER, OPERATION
    }


    @Getter
    @RequiredArgsConstructor
    public enum DegreeRequest {
        MASTER, DOCTOR
    }

    @Getter
    @RequiredArgsConstructor
    public enum EducationContentRequest {
        CONSULTING, CORRECTION, TRANSLATION;
    }

    @Getter
    @RequiredArgsConstructor
    public enum CarTypeRequest {
        COMPACT, MIDSIZE, SUV;
    }

    @Getter
    @RequiredArgsConstructor
    public enum TransactionMethodRequest {
        CHARTER, MONTHLY;
    }

    @Getter
    @RequiredArgsConstructor
    public enum NumberOfRoomsRequest {
        ONEROOM, TWOROOM, TWOBAY;
    }

    @Getter
    @RequiredArgsConstructor
    public enum BookingStatusResponse {
        ACCEPT, WAITING, CANCEL, COMPLETE;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EducationReceiveRequest {

        @NotNull(message = "서비스 신청 내용을 입력해주세요.")
        @Schema(description = "내용", example = "안녕하세요! 교육 서비스를 신청한.. 어쩌구..")
        private String content;

        @Null(message = "서비스 신청시 필요한 파일을 입력해주세요.")
        @Schema(description = "서비스 신청 파일", example = "https://picsum.photos/seed/picsum/200/300")
        private String userFileUrl;

        @NotNull(message = "학위를 선택해주세요..")
        @Schema(description = "학위", example = "MASTER/DOCTOR", required = true)
        private DegreeRequest degreeRequest;

        @NotNull(message = "서비스를 선택해주세요..")
        @Schema(description = "서비스", example = "CONSULTING/CORRECTION/TRANSLATION", required = true)
        private EducationContentRequest educationContentRequest;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EducationReceiveResponse {

        @Schema(description = "서비스를 신청한 유저의 이메일", example = "test@test.com", required = true)
        private String userUsername;

        @Schema(description = "서비스를 처리한 어드민의 이메일", example = "admin@admin.com")
        private String operationUsername;

        @Schema(description = "학위", example = "MASTER/DOCTOR", required = true)
        private DegreeRequest degreeRequest;

        @Schema(description = "서비스", example = "CONSULTING/CORRECTION/TRANSLATION", required = true)
        private EducationContentRequest educationContentRequest;

        @Schema(description = "서비스 신청 내용", example = "안녕하세요! 교육 서비스를 신청한.. 어쩌구..")
        private String userContent;

        @Schema(description = "서비스 승인 내용", example = "안녕하세요! 교육 서비스를 승인한.. 어쩌구..")
        private String adminContent;

        @Schema(description = "서비스 신청시 업로드한 파일", example = "https://picsum.photos/seed/picsum/200/300")
        private String userFileUrl;

        @Schema(description = "서비스 승인시 업로드한 파일", example = "https://picsum.photos/seed/picsum/200/300")
        private String adminFileUrl;

        @Schema(description = "서비스 처리 상태", example = "ACCEPT/WAITING/CANCEL/COMPLETE")
        private BookingStatusResponse bookingStatus;

        @Schema(description = "서비스 생성일자 및 시간", example = "2021-01-01T00:00")
        private LocalDateTime createdAt;

        public static EducationReceiveResponse create(Education education) {
            String operationUsername = null;

            if (education.getAdmin() != null) {
                operationUsername = education.getAdmin().getUsername();
            }

            return EducationReceiveResponse.builder()
                    .userUsername(education.getUser().getUsername())
                    .operationUsername(operationUsername)
                    .degreeRequest(DegreeRequest.valueOf(education.getDegree().name()))
                    .bookingStatus(BookingStatusResponse.valueOf(education.getBookingStatus().name()))
                    .educationContentRequest(EducationContentRequest.valueOf(education.getEducationContent().name()))
                    .userContent(education.getUserContent())
                    .adminContent(education.getAdminContent())
                    .userFileUrl(education.getUserFileUrl())
                    .adminFileUrl(education.getAdminFileUrl())
                    .bookingStatus(BookingStatusResponse.valueOf(education.getBookingStatus().name()))
                    .createdAt(education.getCreatedAt())
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TrafficReceiveRequest {

        @NotNull(message = "서비스 신청 내용을 입력해주세요.")
        @Schema(description = "내용", example = "안녕하세요! 교육 서비스를 신청한.. 어쩌구..")
        private String content;

        @Null(message = "서비스 신청시 필요한 파일을 입력해주세요.")
        @Schema(description = "서비스 신청 파일", example = "https://picsum.photos/seed/picsum/200/300")
        private String userFileUrl;

        @Schema(description = "차종", example = "COMPACT/MIDSIZE/SUV", required = true)
        private CarTypeRequest carTypeRequest;

        @NotNull(message = "희망하는 금액을 입력해주세요..")
        @Schema(description = "금액", example = "500000000", required = true)
        private String price;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TrafficReceiveResponse {

        @Schema(description = "서비스를 신청한 유저의 이메일", example = "test@test.com", required = true)
        private String userUsername;

        @Schema(description = "서비스를 처리한 어드민의 이메일", example = "admin@admin.com")
        private String operationUsername;

        @Schema(description = "차종", example = "COMPACT/MIDSIZE/SUV", required = true)
        private CarTypeRequest carTypeRequest;

        @Schema(description = "금액", example = "500000000", required = true)
        private String price;

        @Schema(description = "서비스 신청 내용", example = "안녕하세요! 교육 서비스를 신청한.. 어쩌구..")
        private String userContent;

        @Schema(description = "서비스 승인 내용", example = "안녕하세요! 교육 서비스를 승인한.. 어쩌구..")
        private String adminContent;

        @Schema(description = "서비스 신청시 업로드한 파일", example = "https://picsum.photos/seed/picsum/200/300")
        private String userFileUrl;

        @Schema(description = "서비스 승인시 업로드한 파일", example = "https://picsum.photos/seed/picsum/200/300")
        private String adminFileUrl;

        @Schema(description = "서비스 처리 상태", example = "ACCEPT/WAITING/CANCEL/COMPLETE")
        private BookingStatusResponse bookingStatus;

        @Schema(description = "서비스 생성일자 및 시간", example = "2021-01-01T00:00")
        private LocalDateTime createdAt;

        public static TrafficReceiveResponse create(Traffic traffic) {
            String operationUsername = null;

            if (traffic.getAdmin() != null) {
                operationUsername = traffic.getAdmin().getUsername();
            }

            return TrafficReceiveResponse.builder()
                    .userUsername(traffic.getUser().getUsername())
                    .operationUsername(operationUsername)
                    .carTypeRequest(CarTypeRequest.valueOf(traffic.getCarType().name()))
                    .price(traffic.getPrice())
                    .userContent(traffic.getUserContent())
                    .adminContent(traffic.getAdminContent())
                    .userFileUrl(traffic.getUserFileUrl())
                    .adminFileUrl(traffic.getAdminFileUrl())
                    .bookingStatus(BookingStatusResponse.valueOf(traffic.getBookingStatus().name()))
                    .createdAt(traffic.getCreatedAt())
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DwellingReceiveRequest {

        @NotNull(message = "서비스 신청 내용을 입력해주세요.")
        @Schema(description = "내용", example = "안녕하세요! 교육 서비스를 신청한.. 어쩌구..", required = true)
        private String content;

        @Null(message = "서비스 신청시 필요한 파일을 입력해주세요.")
        @Schema(description = "서비스 신청 파일", example = "https://picsum.photos/seed/picsum/200/300")
        private String userFileUrl;

        @NotNull(message = "거래 방식을 선택해주세요..")
        @Schema(description = "거래 방식", example = "CHARTER/MONTHLY", required = true)
        private TransactionMethodRequest transactionMethodRequest;

        @NotNull(message = "방의 종류를 선택해주세요..")
        @Schema(description = "주거 형태", example = "ONEROOM/TWOROOM/TWOBAY", required = true)
        private NumberOfRoomsRequest numberOfRoomsRequest;

        @NotNull(message = "희망하는 금액을 입력해주세요..")
        @Schema(description = "금액", example = "500000000", required = true)
        private String price;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DwellingReceiveResponse {

        @Schema(description = "서비스를 신청한 유저의 이메일", example = "test@test.com", required = true)
        private String userUsername;

        @Schema(description = "서비스를 처리한 어드민의 이메일", example = "admin@admin.com")
        private String operationUsername;

        @Schema(description = "거래 방식", example = "CHARTER/MONTHLY", required = true)
        private TransactionMethodRequest transactionMethodRequest;


        @Schema(description = "주거 형태", example = "ONEROOM/TWOROOM/TWOBAY", required = true)
        private NumberOfRoomsRequest numberOfRoomsRequest;

        @Schema(description = "희망 금액", example = "500000000", required = true)
        private String price;

        @Schema(description = "서비스 신청 내용", example = "안녕하세요! 교육 서비스를 신청한.. 어쩌구..")
        private String userContent;

        @Schema(description = "서비스 승인 내용", example = "안녕하세요! 교육 서비스를 승인한.. 어쩌구..")
        private String adminContent;

        @Schema(description = "서비스 신청시 업로드한 파일", example = "https://picsum.photos/seed/picsum/200/300")
        private String userFileUrl;

        @Schema(description = "서비스 승인시 업로드한 파일", example = "https://picsum.photos/seed/picsum/200/300")
        private String adminFileUrl;

        @Schema(description = "서비스 처리 상태", example = "ACCEPT/WAITING/CANCEL/COMPLETE")
        private BookingStatusResponse bookingStatus;

        @Schema(description = "서비스 생성일자 및 시간", example = "2021-01-01T00:00")
        private LocalDateTime createdAt;

        public static DwellingReceiveResponse create(Dwelling dwelling) {
            String operationUsername = null;

            if (dwelling.getAdmin() != null) {
                operationUsername = dwelling.getAdmin().getUsername();
            }

            return DwellingReceiveResponse.builder()
                    .userUsername(dwelling.getUser().getUsername())
                    .operationUsername(operationUsername)
                    .transactionMethodRequest(TransactionMethodRequest.valueOf(dwelling.getTransactionMethod().name()))
                    .numberOfRoomsRequest(NumberOfRoomsRequest.valueOf(dwelling.getNumberOfRooms().name()))
                    .price(dwelling.getPrice())
                    .userContent(dwelling.getUserContent())
                    .adminContent(dwelling.getAdminContent())
                    .userFileUrl(dwelling.getUserFileUrl())
                    .adminFileUrl(dwelling.getAdminFileUrl())
                    .bookingStatus(BookingStatusResponse.valueOf(dwelling.getBookingStatus().name()))
                    .createdAt(dwelling.getCreatedAt())
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommunicationReceiveRequest {

        @NotNull(message = "서비스 신청 내용을 입력해주세요.")
        @Schema(description = "내용", example = "안녕하세요! 교육 서비스를 신청한.. 어쩌구..", required = true)
        private String content;

        @Null(message = "서비스 신청시 필요한 파일을 입력해주세요.")
        @Schema(description = "서비스 신청 파일", example = "https://picsum.photos/seed/picsum/200/300")
        private String userFileUrl;

        @NotNull(message = "휴대폰 기종을 입력해주세요..")
        @Schema(description = "휴대폰 기종", example = "아이폰 14 pro", required = true)
        private String modelName;

        @NotNull(message = "유심칩 신청 여부를 선택해주세요..")
        @Schema(description = "금액", example = "True/False", required = true)
        private boolean usim;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommunicationReceiveResponse {

        @Schema(description = "서비스를 신청한 유저의 이메일", example = "test@test.com", required = true)
        private String userUsername;

        @Schema(description = "서비스를 처리한 어드민의 이메일", example = "admin@admin.com")
        private String operationUsername;

        @Schema(description = "휴대폰 기종", example = "아이폰 14 pro", required = true)
        private String modelName;

        @Schema(description = "금액", example = "True/False", required = true)
        private boolean usim;

        @Schema(description = "서비스 신청 내용", example = "안녕하세요! 교육 서비스를 신청한.. 어쩌구..")
        private String userContent;

        @Schema(description = "서비스 승인 내용", example = "안녕하세요! 교육 서비스를 승인한.. 어쩌구..")
        private String adminContent;

        @Schema(description = "서비스 신청시 업로드한 파일", example = "https://picsum.photos/seed/picsum/200/300")
        private String userFileUrl;

        @Schema(description = "서비스 승인시 업로드한 파일", example = "https://picsum.photos/seed/picsum/200/300")
        private String adminFileUrl;

        @Schema(description = "서비스 처리 상태", example = "ACCEPT/WAITING/CANCEL/COMPLETE")
        private BookingStatusResponse bookingStatus;

        @Schema(description = "서비스 생성일자 및 시간", example = "2021-01-01T00:00")
        private LocalDateTime createdAt;

        public static CommunicationReceiveResponse create(Communication communication) {
            String operationUsername = null;

            if (communication.getAdmin() != null) {
                operationUsername = communication.getAdmin().getUsername();
            }

            return CommunicationReceiveResponse.builder()
                    .userUsername(communication.getUser().getUsername())
                    .operationUsername(operationUsername)
                    .modelName(communication.getModelName())
                    .usim(communication.isUsim())
                    .userContent(communication.getUserContent())
                    .adminContent(communication.getAdminContent())
                    .userFileUrl(communication.getUserFileUrl())
                    .adminFileUrl(communication.getAdminFileUrl())
                    .bookingStatus(BookingStatusResponse.valueOf(communication.getBookingStatus().name()))
                    .createdAt(communication.getCreatedAt())
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ServiceAllResponse {

        @Schema(description = "서비스를 신청한 유저의 이메일", example = "test@test.com", required = true)
        private String userUsername;

        @Schema(description = "서비스를 처리한 어드민의 이메일", example = "admin@admin.com")
        private String operationUsername;

        @Schema(description = "서비스 처리 상태", example = "ACCEPT/WAITING/CANCEL/COMPLETE")
        private BookingStatusResponse bookingStatus;

        @Schema(description = "서비스 생성일자 및 시간", example = "2021-01-01T00:00")
        private LocalDateTime createdAt;

        public static ServiceAllResponse create(Booking booking) {
            String operationUsername = null;

            if (booking.getAdmin() != null) {
                operationUsername = booking.getAdmin().getUsername();
            }

            return ServiceAllResponse.builder()
                    .userUsername(booking.getUser().getUsername())
                    .operationUsername(operationUsername)
                    .bookingStatus(BookingStatusResponse.valueOf(booking.getBookingStatus().name()))
                    .createdAt(booking.getCreatedAt())
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ServiceAcceptRequest {

        @NotNull(message = "서비스 승인 내용을 입력해주세요.")
        @Schema(description = "내용", example = "안녕하세요! 교육 서비스를 승인한.. 어쩌구..")
        private String content;

        @Null(message = "서비스 승인시 첨부할 파일의 url을 입력해주세요.")
        @Schema(description = "서비스 승인 파일", example = "https://picsum.photos/seed/picsum/200/300")
        private String adminFileUrl;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ServiceRejectRequest {

        @NotNull(message = "서비스 거절 사유를 입력해주세요.")
        @Schema(description = "내용", example = "신청한 서비스를 처리할 관리자가 없어 거절합니다... 어쩌구...")
        private String content;
    }
}
