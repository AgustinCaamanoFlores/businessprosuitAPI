package com.businessprosuite.api.dto.asset;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AssetDTO {
    private Integer id;
    private String internalCode;      // actCodInterno
    private String description;       // actDescripcion
    private BigDecimal purchaseValue; // actValorCompra
    private LocalDate acquisitionDate;// actFechaAdq
    private Integer categoryId;       // actCat.id
    private Integer companyId;        // configCompany.id
}
