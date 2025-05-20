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
public class InventoryLotLocationHistoryDTO {
    private Integer id;
    private Integer lotId;
    private Integer warehouseId;
    private LocalDateTime movedAt;
}
