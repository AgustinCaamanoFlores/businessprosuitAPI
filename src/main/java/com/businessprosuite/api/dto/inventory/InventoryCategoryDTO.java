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
public class InventoryCategoryDTO {
    private Integer id;
    private String name;
    private String description;
    private Integer parentId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<InventoryCategoryDTO> subcategories;
}