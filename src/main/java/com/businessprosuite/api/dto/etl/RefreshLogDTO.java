package com.businessprosuite.api.dto.etl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshLogDTO {
    private Integer id;
    private String processName;
    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;
    private Integer rowsProcessed;
    private String status;
    private String message;
}