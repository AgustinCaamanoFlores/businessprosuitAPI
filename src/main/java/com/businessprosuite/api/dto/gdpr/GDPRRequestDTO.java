package com.businessprosuite.api.dto.gdpr;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GDPRRequestDTO {
    private Integer id;
    private Integer userId;
    private String requestType;
    private String status;
    private LocalDateTime submittedAt;
    private LocalDateTime completedAt;
}
