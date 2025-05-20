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
public class EventDTO {
    private Integer id;
    private String eventType;
    private String payloadJson;
    private LocalDateTime occurredAt;
}