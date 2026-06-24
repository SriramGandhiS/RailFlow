package com.railflow.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

// Day 15 — feat: add seat availability fields by class to TrainSchedule
// Generated: 2026-06-24
@Entity
@Table(name = "trainschedules")
@Data
public class TrainSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt = LocalDateTime.now();
}
