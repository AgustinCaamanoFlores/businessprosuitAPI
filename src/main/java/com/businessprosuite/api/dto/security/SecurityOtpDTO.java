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
public class SecurityOtpDTO {
    private Integer id;
    private Integer userId;
    private String code;
    private LocalDateTime issuedAt;
    private LocalDateTime expiresAt;
    private Boolean used;
}