package com.railflow.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TicketDTO {
    private Long id;
    private LocalDateTime createdAt = LocalDateTime.now();
}
