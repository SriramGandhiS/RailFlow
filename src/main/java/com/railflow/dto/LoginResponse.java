package com.railflow.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

// Day 8 — feat: add LoginResponse with accessToken and expiry fields
// Generated: 2026-06-21
@Entity
@Table(name = "loginresponses")
@Data
public class LoginResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt = LocalDateTime.now();
}
