package com.businessprosuite.api.dto.hr;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class WorkerAttendanceDTO {
    private Integer id;
    private Integer workerId;        // hrAttHrW.id
    private Integer shiftId;         // hrAttShift.id
    private LocalDateTime time;      // hrAttTime
    private String type;             // hrAttType
    private String method;           // hrAttMethod
    private Integer deviceId;        // hrAttDeviceId
    private LocalDateTime createdAt; // hrAttCreatedAt
}
