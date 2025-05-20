package com.businessprosuite.api.dto.audit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginAttemptDTO {
    private Integer id;
    private Integer userId;
    private LocalDateTime attemptTime;
    private String ip;
    private Boolean success;
}
