package com.businessprosuite.api.dto.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestResultDTO {
    private Integer id;
    private String testName;
    private String testResult;
    private String testMessage;
    private LocalDateTime testDate;
}
