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
public class ErrorLogDTO {
    private Integer id;
    private String errorMessage;
    private LocalDateTime errorDate;
}