package com.businessprosuite.api.dto.schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchemaVersionDTO {
    private Integer id;
    private String version;
    private LocalDateTime appliedAt;
}