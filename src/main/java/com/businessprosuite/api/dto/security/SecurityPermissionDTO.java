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
public class SecurityPermissionDTO {
    private Integer id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
