package com.businessprosuite.api.dto.config;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ConfigCountryDTO {
    private String code;                // código ISO (PK)
    private String name;                // cfgPaisNombre
    private String accountingStandard;
    private String privacyLaw;
    private Byte dataLocalization;
    private String predominantSector;
    private String defaultCurrency;     // monedaPorDefecto
    private String currencySymbol;      // simboloMoneda
    private String dateFormat;          // formatoFecha
    private Integer taxDecimals;        // decimalesImpuestos
}
