package com.businessprosuite.api.dto.hr;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class WorkerDTO {
    private Integer id;
    private String name;            // hrWName
    private String address;         // hrWAddress
    private String cell;            // hrWCell
    private String homePhone;       // hrWHomePhone
    private String email;           // hrWEmail
    private LocalDate birthDate;    // hrWBirthDate
    private Integer departmentId;   // workerDepartment.id
    private Integer shiftId;        // shift.id
    private Integer companyId;      // hrCmp.id
    private Byte deleted;           // hrWDeleted
    private LocalDateTime createdAt;// hrCreatedAt
    private LocalDateTime updatedAt;// hrUpdatedAt
}
