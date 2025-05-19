package com.businessprosuite.api.dto.hr;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LeaveRequestDTO {
    private Integer id;
    private Integer workerId;
    private Integer leaveTypeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private LocalDateTime appliedAt;
}
