package com.businessprosuite.api.dto.subs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubsPlanDTO {
    private Integer id;
    private String name;
    private BigDecimal price;
    private String period;
    private Integer companyId;
}