package com.businessprosuite.api.model.asset;

import com.businessprosuite.api.model.config.ConfigCompany;
import com.businessprosuite.api.model.leasing.LeasingContract;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@Table(name = "act_activo", schema = "BusinessProSuite")
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "act_id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "act_cod_interno", nullable = false, length = 50)
    private String actCodInterno;

    @Size(max = 255)
    @Column(name = "act_descripcion")
    private String actDescripcion;

    @Column(name = "act_valor_compra", precision = 18, scale = 2)
    private BigDecimal actValorCompra;

    @Column(name = "act_fecha_adq")
    private LocalDate actFechaAdq;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "act_cat_id", nullable = false)
    private AssetCategory actCat;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ColumnDefault("1")
    @JoinColumn(name = "empresa_id", nullable = false)
    private ConfigCompany configCompany;

    @Builder.Default
    @OneToMany(mappedBy = "act")
    private Set<AssetMaintenance> assetMaintenances = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "lcAct")
    private Set<LeasingContract> leasingContracts = new LinkedHashSet<>();

}