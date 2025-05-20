package com.businessprosuite.api.dto.analytics;

import com.businessprosuite.api.model.analytics.DwSaleId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DwSaleDTO {
    private DwSaleId id;
    private Integer quantity;
    private BigDecimal total;
}