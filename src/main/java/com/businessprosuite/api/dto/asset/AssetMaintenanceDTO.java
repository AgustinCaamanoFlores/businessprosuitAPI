package com.businessprosuite.api.dto.asset;

import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AssetMaintenanceDTO {
    private Integer id;
    private Integer assetId;
    private LocalDate maintenanceDate;
    private String detail;
}
