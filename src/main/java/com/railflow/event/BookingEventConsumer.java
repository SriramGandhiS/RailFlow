package com.railflow.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

// Day 39 — feat: add BookingEventConsumer with @KafkaListener on booking-events
// Generated: 2026-07-18
@Entity
@Table(name = "bookingeventconsumers")
@Data
public class BookingEventConsumer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt = LocalDateTime.now();
}
