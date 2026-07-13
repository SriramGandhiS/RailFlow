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

    @Column(nullable = false)
    private String type; // e.g. "EXPRESS", "SUPERFAST", "METRO", "GOVT_BUS", "PRIVATE_BUS"

    @Column(name = "operator_name", nullable = false)
    private String operatorName; // Southern Railways, Chennai Metro, TNSTC, KSRTC
}
