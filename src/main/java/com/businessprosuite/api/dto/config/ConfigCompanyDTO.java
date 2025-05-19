package com.businessprosuite.api.dto.config;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ConfigCompanyDTO {
    private Integer id;
    private String nombre;
    private String monedaBase;
    private String region;
    private String configJson;
    private LocalDateTime creadoAt;
}
