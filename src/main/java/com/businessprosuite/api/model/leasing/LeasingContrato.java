package com.businessprosuite.api.model.leasing;

import com.businessprosuite.api.model.activos.ActActivo;
import com.businessprosuite.api.model.cliente.CusCustomer;
import com.businessprosuite.api.model.configuraciones.CfgEmpresa;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "leasing_contrato", schema = "BusinessProSuite")
public class LeasingContrato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lc_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lc_act_id", nullable = false)
    private ActActivo lcAct;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lc_cus_id", nullable = false)
    private CusCustomer lcCus;

    @NotNull
    @Column(name = "lc_fecha_inicio", nullable = false)
    private LocalDate lcFechaInicio;

    @NotNull
    @Column(name = "lc_fecha_fin", nullable = false)
    private LocalDate lcFechaFin;

    @NotNull
    @Column(name = "lc_cuota", nullable = false, precision = 18, scale = 2)
    private BigDecimal lcCuota;

    @NotNull
    @Lob
    @Column(name = "lc_frecuencia", nullable = false)
    private String lcFrecuencia;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ColumnDefault("1")
    @JoinColumn(name = "empresa_id", nullable = false)
    private CfgEmpresa empresa;

    @Builder.Default
    @OneToMany(mappedBy = "lc")
    private Set<LeasingPago> leasingPagos = new LinkedHashSet<>();

}