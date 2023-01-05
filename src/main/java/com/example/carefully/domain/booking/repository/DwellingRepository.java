package com.example.carefully.domain.booking.repository;

import com.example.carefully.domain.booking.entity.Dwelling;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DwellingRepository extends JpaRepository<Dwelling, Long> {
}
