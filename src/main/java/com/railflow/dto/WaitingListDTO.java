package com.railflow.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class WaitingListDTO {
    private Long id;
    private LocalDateTime createdAt = LocalDateTime.now();
}
