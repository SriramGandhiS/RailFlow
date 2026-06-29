package com.railflow.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

// Day 20 — feat: add SeatClass enum with SLEEPER AC_2T AC_3T GENERAL values
// Generated: 2026-06-29
@Entity
@Table(name = "seatclasss")
@Data
public class SeatClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt = LocalDateTime.now();
}
