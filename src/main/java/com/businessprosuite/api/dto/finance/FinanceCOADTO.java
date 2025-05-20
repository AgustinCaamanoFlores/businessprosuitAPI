package com.businessprosuite.api.dto.finance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinanceCOADTO {
    private Integer id;
    private String code;
    private String name;
    private String type;
    private String description;
    private Integer companyId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}