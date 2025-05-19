package com.businessprosuite.api.dto.config;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ConfigModuleParametersDTO {
    private Integer id;
    private String moduleName;
    private String paramKey;
    private String paramValue;
    private Integer configCompanyId;
}
