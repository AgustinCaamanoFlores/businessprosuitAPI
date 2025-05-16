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
@Table(name = "cfg_settings", schema = "BusinessProSuite", uniqueConstraints = {
        @UniqueConstraint(name = "cfgset_key", columnNames = {"cfgset_key"})
})
public class ConfigSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cfgset_id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "cfgset_key", nullable = false, length = 100)
    private String cfgsetKey;

    @Size(max = 255)
    @NotNull
    @Column(name = "cfgset_value", nullable = false)
    private String cfgsetValue;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "cfgset_created_at", nullable = false)
    private LocalDateTime cfgsetCreatedAt;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "cfgset_updated_at", nullable = false)
    private LocalDateTime cfgsetUpdatedAt;

}