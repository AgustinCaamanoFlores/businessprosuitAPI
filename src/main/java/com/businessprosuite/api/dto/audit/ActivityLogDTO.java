package com.businessprosuite.api.dto.audit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityLogDTO {
    private Integer id;
    private Integer userId;
    private String action;
    private String description;
    private LocalDateTime date;
    private LocalDateTime createdAt;
}