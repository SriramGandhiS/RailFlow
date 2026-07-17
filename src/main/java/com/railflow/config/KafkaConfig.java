package com.railflow.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

// Day 38 — feat: add Kafka producer configuration with serializer settings
// Generated: 2026-07-17
@Entity
@Table(name = "kafkaconfigs")
@Data
public class KafkaConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt = LocalDateTime.now();
}
