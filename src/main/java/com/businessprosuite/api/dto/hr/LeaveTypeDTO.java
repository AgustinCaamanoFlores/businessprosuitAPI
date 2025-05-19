package com.businessprosuite.api.dto.hr;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LeaveTypeDTO {
    private Integer id;
    private String name;
    private Integer maxDays;
}
