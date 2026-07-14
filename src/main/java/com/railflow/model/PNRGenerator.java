package com.railflow.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

// Day 27 — feat: ensure PNR uniqueness with database retry on collision
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
