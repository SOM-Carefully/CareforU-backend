package com.example.carefully.domain.booking.dto;

import com.example.carefully.domain.booking.entity.Booking;
import com.example.carefully.domain.booking.entity.BookingStatus;
import com.example.carefully.domain.user.entity.BusinessType;
import com.example.carefully.domain.user.entity.General;
import com.example.carefully.domain.user.entity.Operation;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;

public class BookingDto {
    @Getter
    @RequiredArgsConstructor
    public enum StateRequest {
        USER, OPERATION
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReceiveRequest {
        @NotNull
        @Size(min = 10, max = 10)
        @ApiModelProperty(example = "2022-11-30")
        private LocalDate requestDate;


        @NotNull
        @Size(min = 8, max = 8)
        @ApiModelProperty(example = "18:34:22")
        private LocalTime requestTime;

        @NotNull
        @Size(min = 7, max = 9)
        @ApiModelProperty(example = "TRANSLATE/DWELLING/TRAFFIC")
        private BusinessType businessType;

        @NotNull
        @Size(min = 3, max = 500)
        @ApiModelProperty(example = "안녕하세요. \n" +
                "저는 컴퓨터과학과 20학번 이혜린입니다.\n" +
                "다름이 아니라 케어풀리라는 팀은\n" +
                "어떤 사람들로 구성되어 있고, 어느 곳에서 이런 팀이 만들어진 것이 궁금하여 문의를 드리게 되었습니다!")
        private String content;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ServiceResponse {
        @NotNull
        @Size(min = 10, max = 10)
        @ApiModelProperty(example = "2022-11-30")
        private LocalDate requestDate;


        @NotNull
        @Size(min = 8, max = 8)
        @ApiModelProperty(example = "18:34:22")
        private LocalTime requestTime;

        @NotNull
        @Size(min = 7, max = 9)
        @ApiModelProperty(example = "TRANSLATE/DWELLING/TRAFFIC")
        private BusinessType businessType;

        @NotNull
        @ApiModelProperty(example = "general@general.com")
        private General general;

        @ApiModelProperty(example = "operation@operation.com")
        private Operation operation;

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
            return ServiceResponse.builder()
                    .requestDate(booking.getRequestDate())
                    .requestTime(booking.getRequestTime())
                    .businessType(booking.getBusinessType())
                    .general(booking.getGeneral())
                    .operation(booking.getOperation())
                    .content(booking.getContent())
                    .bookingStatus(booking.getBookingStatus())
                    .build();
        }
    }
}
