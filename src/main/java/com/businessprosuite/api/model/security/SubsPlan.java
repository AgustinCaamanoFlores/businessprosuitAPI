package com.businessprosuite.api.model.security;

import com.businessprosuite.api.model.configuraciones.CfgEmpresa;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "subs_plan", schema = "BusinessProSuite")
public class SubsPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subs_plan_id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "subs_plan_nombre", nullable = false, length = 100)
    private String subsPlanNombre;

    @NotNull
    @Column(name = "subs_plan_precio", nullable = false, precision = 18, scale = 2)
    private BigDecimal subsPlanPrecio;

    @NotNull
    @Lob
    @Column(name = "subs_plan_periodo", nullable = false)
    private String subsPlanPeriodo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ColumnDefault("1")
    @JoinColumn(name = "empresa_id", nullable = false)
    private CfgEmpresa empresa;

    @Builder.Default
    @OneToMany(mappedBy = "subsPlan")
    private Set<SubsSuscripcion> subsSuscripcions = new LinkedHashSet<>();

}