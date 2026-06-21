package com.railflow.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

// Day 7 — feat: wire JwtFilter into Spring Security filter chain
// Generated: 2026-06-21
@Entity
@Table(name = "securityconfigs")
@Data
public class SecurityConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt = LocalDateTime.now();
}
