package com.railflow.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class WaitingListPromotionDTO {
    private Long id;
    private LocalDateTime createdAt;
}
