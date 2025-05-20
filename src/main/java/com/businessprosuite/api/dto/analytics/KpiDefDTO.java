package com.businessprosuite.api.dto.analytics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KpiDefDTO {
    private Integer id;
    private String name;
    private Integer companyId;
}