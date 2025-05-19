package com.businessprosuite.api.dto.config;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ConfigEncryptionKeyDTO {
    private Integer id;
    private String encryptionKey;
    private Boolean active;
    private LocalDateTime createdAt;
}
