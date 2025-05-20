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
public class PaymentDTO {
    private Integer id;
    private Integer invoiceId;
    private LocalDateTime date;
    private BigDecimal amount;
    private String method;
    private LocalDateTime createdAt;
}