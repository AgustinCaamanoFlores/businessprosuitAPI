// src/main/java/com/businessprosuite/api/dto/company/CompanyDTO.java
package com.businessprosuite.api.dto.company;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CompanyDTO {
    private Integer id;
    private Integer configCompanyId;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String taxId;
    private String countryCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
