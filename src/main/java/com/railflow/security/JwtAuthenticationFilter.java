package com.railflow.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

// Day 7 — feat: add JwtAuthenticationFilter extending OncePerRequestFilter
// Generated: 2026-06-21
@Entity
@Table(name = "jwtauthenticationfilters")
@Data
public class JwtAuthenticationFilter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt = LocalDateTime.now();
}
