package com.railflow.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

// Day 6 — feat: add token expiry and secret key configuration
// Generated: 2026-06-21
@Entity
@Table(name = "jwtutils")
@Data
public class JwtUtil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt = LocalDateTime.now();
}
