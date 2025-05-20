package com.businessprosuite.api.dto.finance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PeriodDTO {
    private Integer id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Byte isClosed;
    private Integer companyId;
    private LocalDateTime createdAt;
}