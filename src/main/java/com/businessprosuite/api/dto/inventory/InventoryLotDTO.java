package com.businessprosuite.api.dto.inventory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryLotDTO {
    private Integer id;
    private Integer productId;
    private String lotNumber;
    private LocalDate expirationDate;
    private Integer quantity;
    private Integer warehouseId;
    private LocalDateTime createdAt;
}