package com.businessprosuite.api.dto.finance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JournalDetailDTO {
    private Integer id;
    private Integer journalId;
    private Integer coaId;
    private BigDecimal debit;
    private BigDecimal credit;
    private String description;
}