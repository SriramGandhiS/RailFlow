package com.railflow.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RouteStopDTO {
    private Long id;
    private LocalDateTime createdAt = LocalDateTime.now();
}
