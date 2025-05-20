package com.businessprosuite.api.dto.inventory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryWerehouseDTO {
    private Integer id;
    private String name;
    private String address;
    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Integer> lotLocationHistoryIds;
    private List<Integer> lotIds;
}
