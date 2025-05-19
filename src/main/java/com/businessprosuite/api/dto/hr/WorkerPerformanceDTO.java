package com.businessprosuite.api.dto.hr;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class WorkerPerformanceDTO {
    private Integer id;
    private Integer workerId;    // hrW.id
    private LocalDate evalDate;
    private BigDecimal score;
    private String remarks;
}
