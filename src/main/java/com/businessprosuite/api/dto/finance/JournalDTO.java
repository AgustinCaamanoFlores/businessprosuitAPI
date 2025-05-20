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
public class JournalDTO {
    private Integer id;
    private LocalDateTime date;
    private String description;
    private Integer companyId;
    private LocalDateTime createdAt;
}