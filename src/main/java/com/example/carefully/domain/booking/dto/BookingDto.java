package com.example.carefully.domain.booking.dto;

import com.example.carefully.domain.booking.entity.Booking;
import com.example.carefully.domain.booking.entity.BookingStatus;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

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
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EducationReceiveRequest {

        @NotNull(message = "서비스 신청 내용을 입력해주세요.")
        @Schema(description = "내용", example = "안녕하세요! 교육 서비스를 신청한.. 어쩌구..", required = true)
        private String content;

        @Null(message = "서비스 신청시 필요한 파일을 입력해주세요.")
        @Schema(description = "서비스 신청 파일", example = "https://picsum.photos/seed/picsum/200/300")
        private String userFileUrl;

        @NotNull(message = "학위를 선택해주세요..")
        @Schema(description = "학위", example = "MASTER/DOCTOR", required = true)
        private DegreeRequest degreeRequest;

        @NotNull(message = "서비스를 선택해주세요..")
        @Schema(description = "학위", example = "CONSULTING/CORRECTION/TRANSLATION", required = true)
        private EducationContentRequest educationContentRequest;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TrafficReceiveRequest {

        @NotNull(message = "서비스 신청 내용을 입력해주세요.")
        @Schema(description = "내용", example = "안녕하세요! 교육 서비스를 신청한.. 어쩌구..", required = true)
        private String content;

        @Null(message = "서비스 신청시 필요한 파일을 입력해주세요.")
        @Schema(description = "서비스 신청 파일", example = "https://picsum.photos/seed/picsum/200/300")
        private String userFileUrl;

        @NotNull(message = "자동차 종류를 선택해주세요..")
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
    public static class CommunicationReceiveRequest {

        @NotNull(message = "서비스 신청 내용을 입력해주세요.")
        @Schema(description = "내용", example = "안녕하세요! 교육 서비스를 신청한.. 어쩌구..", required = true)
        private String content;

        @Null(message = "서비스 신청시 필요한 파일을 입력해주세요.")
        @Schema(description = "서비스 신청 파일", example = "https://picsum.photos/seed/picsum/200/300")
        private String userFileUrl;

        @NotNull(message = "거래 방식을 선택해주세요..")
        @Schema(description = "거래 방식", example = "CHARTER/MONTHLY", required = true)
        private TransactionMethodRequest transactionMethodRequest;

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
    public static class ServiceResponse {
        @NotNull
        @ApiModelProperty(example = "general@general.com")
        private String userUsername;

        @ApiModelProperty(example = "operation@operation.com")
        private String operationUsername;

        @NotNull
        @ApiModelProperty(example = "ACCEPT/WAITING/CANCEL")
        private BookingStatus bookingStatus;

        @NotNull
        @Size(min = 3, max = 500)
        @ApiModelProperty(example = "안녕하세요. \n" +
                "저는 컴퓨터과학과 20학번 이혜린입니다.\n" +
                "다름이 아니라 케어풀리라는 팀은\n" +
                "어떤 사람들로 구성되어 있고, 어느 곳에서 이런 팀이 만들어진 것이 궁금하여 문의를 드리게 되었습니다!")
        private String content;

        public static ServiceResponse create(Booking booking) {
            String operationUsername = null;

            if (booking.getAdmin() != null) {
                operationUsername = booking.getAdmin().getUsername();
            }

            return ServiceResponse.builder()
                    .userUsername(booking.getUser().getUsername())
                    .operationUsername(operationUsername)
                    .content(booking.getContent())
                    .bookingStatus(booking.getBookingStatus())
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateRequest {

        @NotNull
        @Size(min = 3, max = 500)
        @ApiModelProperty(example = "안녕하세요. \n" +
                "저는 컴퓨터과학과 20학번 이혜린입니다.\n" +
                "다름이 아니라 케어풀리라는 팀은\n" +
                "어떤 사람들로 구성되어 있고, 어느 곳에서 이런 팀이 만들어진 것이 궁금하여 문의를 드리게 되었습니다!")
        private String content;
    }
}
