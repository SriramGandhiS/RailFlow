package com.railflow.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

// Day 6 — feat: add UserMapper for entity to DTO conversion
// Generated: 2026-06-21
@Entity
@Table(name = "usermappers")
@Data
public class UserMapper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt = LocalDateTime.now();
}
