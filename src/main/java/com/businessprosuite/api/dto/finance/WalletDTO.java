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
public class WalletDTO {
    private Integer id;
    private Integer securityUserId;
    private BigDecimal balance;
    private String currencyCode;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}