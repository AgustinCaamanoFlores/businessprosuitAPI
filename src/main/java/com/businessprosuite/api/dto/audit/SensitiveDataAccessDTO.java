package com.businessprosuite.api.dto.audit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SensitiveDataAccessDTO {
    private Integer id;
    private Integer userId;
    private LocalDateTime accessedAt;
    private String accessedField;
    private String accessReason;
}