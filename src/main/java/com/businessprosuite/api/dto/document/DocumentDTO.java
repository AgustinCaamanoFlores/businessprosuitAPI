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
public class DocumentDTO {
    private Integer id;
    private String name;
    private String type;
    private String url;
    private Integer companyId;
    private LocalDateTime createdAt;
}