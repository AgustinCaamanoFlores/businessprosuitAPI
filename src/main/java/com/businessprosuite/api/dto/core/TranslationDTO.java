package com.businessprosuite.api.dto.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TranslationDTO {
    private Integer id;
    private String lang;
    private String key;
    private String value;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
