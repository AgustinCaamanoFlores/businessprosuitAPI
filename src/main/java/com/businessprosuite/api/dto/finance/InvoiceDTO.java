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
public class InvoiceDTO {
    private Integer id;
    private Integer configCompanyId;
    private LocalDateTime date;
    private Integer customerId;
    private BigDecimal total;
    private BigDecimal tax;
    private BigDecimal discount;
    private Integer securityUserId;
    private String paymentStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private BigDecimal net;
}