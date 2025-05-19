package com.businessprosuite.api.dto.hr;

import lombok.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ShiftDTO {
    private Integer id;
    private String name;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDateTime createdAt;
}
