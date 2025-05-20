package com.businessprosuite.api.dto.finance;

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
public class CurrencyDTO {
    private Integer id;
    private String code;
    private String name;
    private BigDecimal exchangeRate;
    private LocalDateTime lastUpdated;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
