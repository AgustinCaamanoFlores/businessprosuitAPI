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
public class SchemaChangelogDTO {
    private Integer id;
    private String description;
    private LocalDateTime changeDate;
    private String appliedBy;
    private String changeType;
    private String author;
    private String comments;
}