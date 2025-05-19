package com.businessprosuite.api.dto.user;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserConsentDTO {
    private Integer id;
    private Integer userId;
    private String type;
    private LocalDateTime grantedAt;
    private LocalDateTime revokedAt;
}
