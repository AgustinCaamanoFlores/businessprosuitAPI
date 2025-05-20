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
public class InvoiceDetailDTO {
    private Integer id;
    private Integer invoiceId;
    private Integer productId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;
}
