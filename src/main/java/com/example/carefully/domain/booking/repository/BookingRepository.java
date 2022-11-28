package com.example.carefully.domain.booking.repository;

import com.example.carefully.domain.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
