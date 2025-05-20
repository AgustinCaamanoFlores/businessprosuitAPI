package com.businessprosuite.api.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationTemplateDTO {
    private Integer id;
    private String name;
    private String subject;
    private String body;
}
