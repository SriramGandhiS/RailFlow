package com.railflow.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

// Day 21 — feat: configure Redis CacheManager with 10-minute TTL
// Generated: 2026-06-30
@Entity
@Table(name = "cacheconfigs")
@Data
public class CacheConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt = LocalDateTime.now();
}
