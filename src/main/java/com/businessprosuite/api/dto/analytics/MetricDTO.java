package com.businessprosuite.api.dto.analytics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetricDTO {
    private Integer id;
    private String name;
    private BigDecimal valueNum;
    private LocalDateTime recordedAt;
}