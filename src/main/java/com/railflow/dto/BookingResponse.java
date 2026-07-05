package com.railflow.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

// Day 26 — feat: add BookingResponse DTO with booking summary and PNR
// Generated: 2026-07-05
@Entity
@Table(name = "bookingresponses")
@Data
public class BookingResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt = LocalDateTime.now();
}
