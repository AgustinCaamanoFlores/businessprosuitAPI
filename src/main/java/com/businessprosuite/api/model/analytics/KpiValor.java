package com.businessprosuite.api.model.analytics;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "kpi_valor", schema = "BusinessProSuite")
public class KpiValor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kpv_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "kpi_id", nullable = false)
    private KpiDef kpi;

    @NotNull
    @Column(name = "kpv_periodo", nullable = false)
    private LocalDate kpvPeriodo;

    @Column(name = "kpv_valor", precision = 18, scale = 4)
    private BigDecimal kpvValor;

}