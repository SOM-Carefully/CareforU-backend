package com.example.carefully.domain.booking.repository;

import com.example.carefully.domain.booking.dto.BookingDto;
import com.example.carefully.domain.booking.entity.Booking;
import com.example.carefully.domain.user.entity.BusinessType;
import com.example.carefully.domain.user.entity.General;
import com.example.carefully.domain.user.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<BookingDto.ServiceResponse> getBookingsByGeneralOrderByByCreatedAtDest(General general);
    List<BookingDto.ServiceResponse> getBookingsByOperationOrderByByCreatedAtDest(Operation operation);
    List<BookingDto.ServiceResponse> getBookingsByBusinessTypeOrderByByCreatedAtDest(BusinessType businessType);
}