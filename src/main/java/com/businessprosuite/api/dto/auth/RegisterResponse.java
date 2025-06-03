package com.businessprosuite.api.dto.auth;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterResponse {
    
    private String message;
    private UserInfo user;
    private CompanyInfo company;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserInfo {
        private Integer id;
        private String username;
        private String email;
        private String fullName;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CompanyInfo {
        private Integer id;
        private String name;
        private String email;
        private String address;
    }
} 