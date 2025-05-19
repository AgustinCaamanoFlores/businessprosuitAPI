package com.businessprosuite.api.dto.asset;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AssetCategoryDTO {
    private Integer id;
    private String name;        // actCatNombre
    private Integer companyId;  // configCompany.id
}
