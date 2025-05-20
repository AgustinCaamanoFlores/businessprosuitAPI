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
public class ConversionDTO {
    private Integer id;
    private Integer fromCurrencyId;
    private Integer toCurrencyId;
    private BigDecimal factor;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}