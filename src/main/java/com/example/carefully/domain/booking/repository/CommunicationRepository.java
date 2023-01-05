package com.example.carefully.domain.booking.repository;

import com.example.carefully.domain.booking.entity.Communication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunicationRepository extends JpaRepository<Communication, Long> {
}
