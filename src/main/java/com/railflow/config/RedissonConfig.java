package com.railflow.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

// Day 31 — feat: add RedissonClient configuration bean
// Generated: 2026-07-10
@Entity
@Table(name = "redissonconfigs")
@Data
public class RedissonConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt = LocalDateTime.now();
}
