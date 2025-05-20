package com.businessprosuite.api.dto.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SecurityPasswordHistoryDTO {
    private Integer id;
    private Integer userId;
    private String passwordHash;
    private OffsetDateTime changedAt;
}