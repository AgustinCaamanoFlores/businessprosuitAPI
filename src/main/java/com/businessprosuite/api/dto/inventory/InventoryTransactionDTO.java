package com.businessprosuite.api.dto.inventory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryTransactionDTO {
    private Integer id;
    private Integer productId;
    private LocalDateTime date;
    private String type;
    private Integer quantity;
    private String description;
    private LocalDateTime createdAt;
}