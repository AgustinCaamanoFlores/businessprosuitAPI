package com.businessprosuite.api.dto.analytics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KpiValorDTO {
    private Integer id;
    private Integer kpiId;
    private LocalDate period;
    private BigDecimal value;
}