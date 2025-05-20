package com.businessprosuite.api.dto.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentVersionDTO {
    private Integer id;
    private Integer documentId;
    private Integer versionNumber;
    private String url;
    private LocalDateTime createdAt;
}