// src/main/java/com/businessprosuite/api/dto/company/BranchDTO.java
package com.businessprosuite.api.dto.company;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CompanyBranchDTO {
    private Integer id;
    private Integer companyId;
    private String name;
    private String address;
    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
