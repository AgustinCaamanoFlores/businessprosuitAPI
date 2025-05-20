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
public class LeasingContractDTO {
    private Integer id;
    private Integer assetId;
    private Integer customerId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal quota;
    private String frequency;
    private Integer companyId;
}
