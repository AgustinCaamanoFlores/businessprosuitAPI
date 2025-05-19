package com.businessprosuite.api.dto.hr;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class WorkerDepartmentDTO {
    private Integer id;
    private String name;           // hrDeptName
    private LocalDateTime createdAt;// hrDeptCreatedAt
}
