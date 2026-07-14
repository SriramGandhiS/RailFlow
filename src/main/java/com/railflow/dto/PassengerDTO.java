package com.railflow.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PassengerDTO {
    private Long id;
    private LocalDateTime createdAt = LocalDateTime.now();
}
