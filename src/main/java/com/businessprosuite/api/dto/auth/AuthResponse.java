package com.businessprosuite.api.dto.auth;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    
    private String token;
    private String type = "Bearer";
    private String username;
    private String email;
    private String fullName;
    private List<String> authorities;
    private Long expiresIn;
    
    public AuthResponse(String token, String username, String email, String fullName, List<String> authorities, Long expiresIn) {
        this.token = token;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.authorities = authorities;
        this.expiresIn = expiresIn;
    }
} 