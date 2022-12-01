package com.example.carefully.domain.booking.repository;

import com.example.carefully.domain.booking.dto.BookingDto;
import com.example.carefully.domain.booking.entity.Booking;
import com.example.carefully.domain.user.entity.BusinessType;
import com.example.carefully.domain.user.entity.General;
import com.example.carefully.domain.user.entity.Operation;
import com.example.carefully.global.dto.SliceDto;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Slice<Booking> findAllByGeneral(General general);
    Slice<Booking> findAllByOperation(Operation operation);
    Slice<Booking> findAllByBusinessType(BusinessType businessType);
}