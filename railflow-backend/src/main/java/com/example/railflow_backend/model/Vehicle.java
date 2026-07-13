package com.example.railflow_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vehicles")
@Data
@NoArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String number; // Train No, Bus Plate, or Metro identifier

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType type;

    @Column(name = "operator_name", nullable = false)
    private String operatorName;

    @Column(name = "sleeper_capacity", nullable = false)
    private int sleeperCapacity;

    @Column(name = "ac_capacity", nullable = false)
    private int acCapacity;

    @Column(name = "general_capacity", nullable = false)
    private int generalCapacity;
}
