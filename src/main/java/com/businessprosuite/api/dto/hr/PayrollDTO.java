package com.businessprosuite.api.dto.hr;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PayrollDTO {
    private Integer id;
    private Integer workerId;
    private BigDecimal baseSalary;
    private BigDecimal bonus;
    private BigDecimal deductions;
    private LocalDate payDate;
    private String remarks;
}
