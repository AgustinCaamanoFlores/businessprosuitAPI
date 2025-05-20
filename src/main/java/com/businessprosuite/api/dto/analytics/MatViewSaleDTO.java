package com.businessprosuite.api.dto.analytics;

import com.businessprosuite.api.model.analytics.MatViewSaleId;
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
public class MatViewSaleDTO {
    private MatViewSaleId id;
    private BigDecimal totalSales;
    private LocalDateTime updatedAt;
}