package com.businessprosuite.api.dto.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SecurityRoleDTO {
    private Integer id;
    private String name;
    private String description;
    private Integer parentRoleId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}