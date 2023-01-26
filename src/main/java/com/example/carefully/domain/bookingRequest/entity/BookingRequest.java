package com.example.carefully.domain.bookingRequest.entity;

import com.example.carefully.domain.booking.dto.BookingDto;
import com.example.carefully.domain.booking.entity.Booking;
import com.example.carefully.domain.user.entity.User;
import com.example.carefully.global.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.example.carefully.domain.bookingRequest.entity.BookingStatus.*;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table
@NoArgsConstructor(access = PROTECTED)
public class BookingRequest extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @OneToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private User admin;

    @Column
    private String userContent;

    @Column
    private String adminContent;

    @Column(nullable = false)
    @Enumerated(value = STRING)
    private BookingStatus bookingStatus;

    @Column(nullable = false)
    private BusinessType businessType;

    private String userFileUrl;

    private String adminFileUrl;

    @Builder
    public BookingRequest(Long id, Booking booking, User user, User admin, String userContent, String adminContent, BookingStatus bookingStatus, BusinessType businessType, String userFileUrl, String adminFileUrl) {
        this.id = id;
        this.booking = booking;
        this.user = user ;
        this.admin = admin;
        this.userContent = userContent;
        this.adminContent = adminContent;
        this.bookingStatus = bookingStatus;
        this.businessType = businessType;
        this.userFileUrl = userFileUrl;
        this.adminFileUrl = adminFileUrl;
    }

    public static BookingRequest educationBookingRequest(Booking booking, User user, BookingDto.EducationReceiveRequest receiveRequest) {
        return BookingRequest.builder()
                .booking(booking)
                .user(user)
                .businessType(BusinessType.valueOf("EDUCATION"))
                .userContent(receiveRequest.getContent())
                .userFileUrl(receiveRequest.getUserFileUrl())
                .bookingStatus(BookingStatus.valueOf("WAITING"))
                .build();
    }

    public static BookingRequest communicationBookingRequest(Booking booking, User user, BookingDto.CommunicationReceiveRequest receiveRequest) {
        return BookingRequest.builder()
                .booking(booking)
                .user(user)
                .businessType(BusinessType.valueOf("COMMUNICATION"))
                .userContent(receiveRequest.getContent())
                .userFileUrl(receiveRequest.getUserFileUrl())
                .bookingStatus(BookingStatus.valueOf("WAITING"))
                .build();
    }

    public static BookingRequest dwellingBookingRequest(Booking booking, User user, BookingDto.DwellingReceiveRequest receiveRequest) {
        return BookingRequest.builder()
                .booking(booking)
                .user(user)
                .businessType(BusinessType.valueOf("DWELLING"))
                .userContent(receiveRequest.getContent())
                .userFileUrl(receiveRequest.getUserFileUrl())
                .bookingStatus(BookingStatus.valueOf("WAITING"))
                .build();
    }

    public static BookingRequest trafficBookingRequest(Booking booking, User user, BookingDto.TrafficReceiveRequest receiveRequest) {
        return BookingRequest.builder()
                .booking(booking)
                .user(user)
                .businessType(BusinessType.valueOf("DWELLING"))
                .userContent(receiveRequest.getContent())
                .userFileUrl(receiveRequest.getUserFileUrl())
                .bookingStatus(BookingStatus.valueOf("WAITING"))
                .build();
    }

    //== 비지니스 메서드 ==//
    public void setAdmin(User currentUser) {
        this.admin = currentUser;
    }

    public void accept(BookingDto.ServiceAcceptRequest serviceAcceptRequest) {
        this.bookingStatus = ACCEPT;
        this.adminContent = serviceAcceptRequest.getContent();
        this.adminFileUrl = serviceAcceptRequest.getAdminFileUrl();
    }

    public void cancel(BookingDto.ServiceRejectRequest serviceRejectRequest) {
        this.bookingStatus = CANCEL;
        this.adminContent = serviceRejectRequest.getContent();
    }

    public void ongoing() {
        this.bookingStatus = ONGOING;
    }

    public void complete() {
        this.bookingStatus = COMPLETE;
    }

    public void setNullAdmin() { this.admin = null; }
}
