package com.businessprosuite.api.dto.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SecurityPasswordPolicyDTO {
    private Integer id;
    private String description;
    private Integer minLength;
    private Boolean requireUpper;
    private Boolean requireDigit;
    private Integer expireDays;
    private Integer reuseForbid;
}
