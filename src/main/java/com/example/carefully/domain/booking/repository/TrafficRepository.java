package com.example.carefully.domain.booking.repository;

import com.example.carefully.domain.booking.entity.Traffic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrafficRepository extends JpaRepository<Traffic, Long> {
}
