package com.example.carefully.domain.booking.dto;

import com.example.carefully.domain.booking.entity.Booking;
import com.example.carefully.domain.booking.entity.BookingStatus;
import com.example.carefully.domain.user.entity.BusinessType;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
        @ApiModelProperty(example = "2018-07-26T01:20:00")
        private LocalDateTime requestTime;

        @NotNull
        @ApiModelProperty(example = "TRANSLATE/DWELLING/TRAFFIC")
        private BusinessType businessType;

        @NotNull
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
        @ApiModelProperty(example = "2018-07-26T01:20:00")
        private LocalDateTime requestTime;

        @NotNull
        @ApiModelProperty(example = "TRANSLATE/DWELLING/TRAFFIC")
        private BusinessType businessType;

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

            if (booking.getOperation() != null) {
                operationUsername = booking.getOperation().getUsername();
            }

            return ServiceResponse.builder()
                    .requestTime(booking.getRequestTime())
                    .businessType(booking.getBusinessType())
                    .userUsername(booking.getGeneral().getUsername())
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
        @ApiModelProperty(example = "2018-07-26T01:20:00")
        private LocalDateTime requestTime;

        @NotNull
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
}
