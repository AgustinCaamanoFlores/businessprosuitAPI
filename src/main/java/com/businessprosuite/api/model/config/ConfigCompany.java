package com.businessprosuite.api.model.config;

import com.businessprosuite.api.model.analytics.KpiDef;
import com.businessprosuite.api.model.asset.Asset;
import com.businessprosuite.api.model.asset.AssetCategory;
import com.businessprosuite.api.model.customer.Customer;
import com.businessprosuite.api.model.company.Company;
import com.businessprosuite.api.model.finance.Invoice;
import com.businessprosuite.api.model.inventory.InventoryProduct;
import com.businessprosuite.api.model.leasing.LeasingContract;
import com.businessprosuite.api.model.subs.SubsPlan;
import com.businessprosuite.api.model.subs.SubsSuscription;
import com.businessprosuite.api.model.workflow.WorkflowDefinition;
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
public class ConfigCompany {
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
    @OneToMany(mappedBy = "configCompany")
    private Set<Asset> assets = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "configCompany")
    private Set<AssetCategory> categories = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "configCompany")
    private Set<ConfigModuleParameters> configModuleParameters = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "configCompany")
    private Set<Company> comCompanies = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "configCompany")
    private Set<Customer> customers = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "configCompany")
    private Set<Invoice> invoices = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "configCompany")
    private Set<InventoryProduct> inventoryProducts = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "configCompany")
    private Set<KpiDef> kpiDefs = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "configCompany")
    private Set<LeasingContract> leasingContracts = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "configCompany")
    private Set<SubsPlan> subsPlans = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "configCompany")
    private Set<SubsSuscription> subsSuscriptions = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "configCompany")
    private Set<WorkflowDefinition> workflowDefinitions = new LinkedHashSet<>();

}