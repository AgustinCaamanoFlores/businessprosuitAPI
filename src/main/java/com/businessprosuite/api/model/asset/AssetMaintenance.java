package com.businessprosuite.api.model.asset;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "act_mantenimiento", schema = "BusinessProSuite")
public class AssetMaintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "act_mant_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "act_id", nullable = false)
    private Asset act;

    @NotNull
    @Column(name = "act_mant_fecha", nullable = false)
    private LocalDate actMantFecha;

    @Lob
    @Column(name = "act_mant_detalle")
    private String actMantDetalle;

}