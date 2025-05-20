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
public class SecurityUserDTO {
    private Integer id;
    private String name;
    private String password;
    private String email;
    private Byte available;
    private LocalDateTime lastLogin;
    private Byte active;
    private Byte mfaEnabled;
    private String mfaSecret;
    private Integer roleId;
    private Integer companyId;
    private LocalDateTime lastPasswordChange;
    private Integer failedAttempts;
    private String residence;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}