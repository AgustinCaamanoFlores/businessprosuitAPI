package com.businessprosuite.api.dto.config;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ConfigComplianceDTO {
    private Integer companyId;      // parte 1 de la PK
    private String complianceCode;  // parte 2 de la PK (ajusta el nombre según tu ID)
    private Byte mandatory;
}
