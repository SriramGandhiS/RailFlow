package com.railflow.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

// Day 25 — feat: add BookingRequest DTO with scheduleId and passenger list
// Generated: 2026-07-04
@Entity
@Table(name = "bookingrequests")
@Data
public class BookingRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt = LocalDateTime.now();
}
