package com.railflow.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class StationDTO {
    private Long id;
    private LocalDateTime createdAt = LocalDateTime.now();
}
