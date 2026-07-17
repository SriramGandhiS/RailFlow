package com.railflow.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

// Day 38 — feat: add BookingEvent record with event type and booking details
// Generated: 2026-07-17
@Entity
@Table(name = "bookingevents")
@Data
public class BookingEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt = LocalDateTime.now();
}
