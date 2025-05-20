package com.businessprosuite.api.dto.finance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinanceConsReportDTO {
    private Integer id;
    private Integer companyId;
    private LocalDate reportDate;
    private BigDecimal totalAssets;
    private BigDecimal totalLiabilities;
    private BigDecimal netIncome;
    private String currency;
    private BigDecimal conversionFactor;
    private LocalDateTime createdAt;
}