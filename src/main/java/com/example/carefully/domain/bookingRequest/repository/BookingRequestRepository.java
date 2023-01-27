package com.example.carefully.domain.bookingRequest.repository;

import com.example.carefully.domain.booking.entity.Booking;
import com.example.carefully.domain.bookingRequest.entity.BookingRequest;
import com.example.carefully.domain.user.entity.User;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRequestRepository extends JpaRepository<BookingRequest, Long> {
    BookingRequest findByIdAndUser(Long bookingId, User user);

    Slice<BookingRequest> findAllByOrderByCreatedAtDesc();

    Slice<BookingRequest> findAllByUser(User user);

    Slice<BookingRequest> findAllByAdmin(User admin);
}
