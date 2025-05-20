package com.businessprosuite.api.dto.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SecurityPolicyRuleDTO {
    private Integer id;
    private String expr;
    private String description;
}