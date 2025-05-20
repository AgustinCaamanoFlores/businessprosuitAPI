package com.businessprosuite.api.dto.leasing;

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
public class LeasingPaymentDTO {
    private Integer id;
    private Integer contractId;
    private LocalDate dueDate;
    private BigDecimal amount;
    private String status;
}