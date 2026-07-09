package com.railflow.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

// Day 30 — refactor: extract seat assignment to dedicated SeatAllocator service
// Generated: 2026-07-09
@Entity
@Table(name = "seatallocators")
@Data
public class SeatAllocator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt = LocalDateTime.now();
}
