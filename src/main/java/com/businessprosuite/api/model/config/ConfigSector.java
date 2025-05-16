package com.businessprosuite.api.model.config;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cfg_sector", schema = "BusinessProSuite")
public class ConfigSector {
    @Id
    @Column(name = "cfg_sector_id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "cfg_sector_nombre", nullable = false, length = 50)
    private String cfgSectorNombre;

}