package com.businessprosuite.api.model.configuraciones;

import com.businessprosuite.api.model.activos.ActActivo;
import com.businessprosuite.api.model.activos.ActCategoria;
import com.businessprosuite.api.model.cliente.CusCustomer;
import com.businessprosuite.api.model.company.ComCompany;
import com.businessprosuite.api.model.finanzas.FinInvoice;
import com.businessprosuite.api.model.inventarios.InvProduct;
import com.businessprosuite.api.model.leasing.LeasingContrato;
import com.businessprosuite.api.model.security.SubsPlan;
import com.businessprosuite.api.model.security.SubsSuscripcion;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cfg_empresa", schema = "BusinessProSuite")
public class CfgEmpresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "emp_nombre", nullable = false, length = 100)
    private String empNombre;

    @Size(max = 3)
    @NotNull
    @Column(name = "emp_moneda_base", nullable = false, length = 3)
    private String empMonedaBase;

    @Size(max = 2)
    @NotNull
    @Column(name = "emp_region", nullable = false, length = 2)
    private String empRegion;

    @Lob
    @Column(name = "emp_config")
    @JdbcTypeCode(SqlTypes.JSON)
    private String empConfig;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "emp_creado_at", nullable = false)
    private LocalDateTime empCreadoAt;

    @Builder.Default
    @OneToMany(mappedBy = "empresa")
    private Set<ActActivo> actActivos = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "empresa")
    private Set<ActCategoria> actCategorias = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "empresa")
    private Set<CfgModuleParam> cfgModuleParams = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "empresa")
    private Set<ComCompany> comCompanies = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "empresa")
    private Set<CusCustomer> cusCustomers = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "empresa")
    private Set<FinInvoice> finInvoices = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "empresa")
    private Set<InvProduct> invProducts = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "empresa")
    private Set<com.businessprosuite.api.model.KpiDef> kpiDefs = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "empresa")
    private Set<LeasingContrato> leasingContratoes = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "empresa")
    private Set<SubsPlan> subsPlans = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "empresa")
    private Set<SubsSuscripcion> subsSuscripcions = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "empresa")
    private Set<com.businessprosuite.api.model.WfDef> wfDefs = new LinkedHashSet<>();

}