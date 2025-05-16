package com.businessprosuite.api.model.config;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cfg_encryption_keys", schema = "BusinessProSuite", uniqueConstraints = {
        @UniqueConstraint(name = "active_key", columnNames = {"is_active"})
})
public class ConfigEncryptionKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "key_id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "encryption_key", nullable = false)
    private String encryptionKey;

    @ColumnDefault("0")
    @Column(name = "is_active")
    private Boolean isActive;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

}