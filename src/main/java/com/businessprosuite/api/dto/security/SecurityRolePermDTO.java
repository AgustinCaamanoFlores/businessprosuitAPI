package com.businessprosuite.api.dto.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SecurityRolePermDTO {
    private Integer id;
    private Integer roleId;
    private Integer permissionId;
}