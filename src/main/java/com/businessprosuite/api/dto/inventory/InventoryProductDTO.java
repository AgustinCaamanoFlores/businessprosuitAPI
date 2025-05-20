package com.businessprosuite.api.dto.inventory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryProductDTO {
    private Integer id;
    private Integer companyId;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private Integer categoryId;
    private Integer supplierId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer reorderLevel;
}
