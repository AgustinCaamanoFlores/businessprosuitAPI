package com.businessprosuite.api.dto.analytics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DwTimeDTO {
    private LocalDate date;
    private Byte day;
    private Byte month;
    private Integer year;
}