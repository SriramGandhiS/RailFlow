package com.railflow.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

// Day 4 — feat: add Ticket entity with seat and coach info
// Generated: 2026-06-21
@Entity
@Table(name = "tickets")
@Data
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt = LocalDateTime.now();
}
