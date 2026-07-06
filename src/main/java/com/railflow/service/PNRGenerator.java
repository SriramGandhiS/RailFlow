package com.railflow.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

// Day 27 — feat: add PNRGenerator service with UUID-based alphanumeric logic
// Generated: 2026-07-06
@Entity
@Table(name = "pnrgenerators")
@Data
public class PNRGenerator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt = LocalDateTime.now();
}
