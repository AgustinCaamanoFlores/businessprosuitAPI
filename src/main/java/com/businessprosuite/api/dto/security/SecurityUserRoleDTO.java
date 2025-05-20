package com.businessprosuite.api.dto.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SecurityUserRoleDTO {
    private Integer id;
    private Integer userId;
    private Integer roleId;
}