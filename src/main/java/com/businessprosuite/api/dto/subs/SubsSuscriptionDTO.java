package com.businessprosuite.api.dto.subs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubsSuscriptionDTO {
    private Integer id;
    private Integer customerId;
    private Integer planId;
    private LocalDate startDate;
    private LocalDate nextCharge;
    private String status;
    private Integer companyId;
}