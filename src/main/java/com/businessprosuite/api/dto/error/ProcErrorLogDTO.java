package com.businessprosuite.api.dto.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcErrorLogDTO {
    private Integer id;
    private String procedureName;
    private String errorMessage;
    private LocalDateTime errorTimestamp;
}