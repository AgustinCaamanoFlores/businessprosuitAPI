package com.businessprosuite.api.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationQueueDTO {
    private Integer id;
    private Integer templateId;
    private Integer userId;
    private String payloadJson;
    private LocalDateTime sendAt;
    private Boolean sent;
    private String resultMessage;
}