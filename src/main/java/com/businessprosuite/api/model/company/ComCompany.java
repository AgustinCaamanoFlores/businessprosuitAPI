package com.businessprosuite.api.model.company;

import com.businessprosuite.api.model.cliente.CusCustomer;
import com.businessprosuite.api.model.configuraciones.CfgCompliance;
import com.businessprosuite.api.model.configuraciones.CfgEmpresa;
import com.businessprosuite.api.model.configuraciones.CfgPai;
import com.businessprosuite.api.model.documentos.DocDocument;
import com.businessprosuite.api.model.finanzas.FinCoa;
import com.businessprosuite.api.model.finanzas.FinConsReport;
import com.businessprosuite.api.model.finanzas.FinJournal;
import com.businessprosuite.api.model.finanzas.FinPeriod;
import com.businessprosuite.api.model.recursoshumanos.HrWorker;
import com.businessprosuite.api.model.security.SecUser;
import com.businessprosuite.api.model.user.UsrUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "com_company", schema = "BusinessProSuite", uniqueConstraints = {
        @UniqueConstraint(name = "cmp_name_UNIQUE", columnNames = {"cmp_name"})
})
public class ComCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cmp_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ColumnDefault("1")
    @JoinColumn(name = "empresa_id", nullable = false)
    private CfgEmpresa empresa;

    @Size(max = 100)
    @NotNull
    @Column(name = "cmp_name", nullable = false, length = 100)
    private String cmpName;

    @Size(max = 200)
    @Column(name = "cmp_address", length = 200)
    private String cmpAddress;

    @Size(max = 50)
    @Column(name = "cmp_phone", length = 50)
    private String cmpPhone;

    @Size(max = 100)
    @Column(name = "cmp_email", length = 100)
    private String cmpEmail;

    @Size(max = 50)
    @Column(name = "cmp_tax_id", length = 50)
    private String cmpTaxId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cmp_pais_codigo", nullable = false)
    private CfgPai cmpPaisCodigo;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "cmp_created_at", nullable = false)
    private LocalDateTime cmpCreatedAt;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "cmp_updated_at", nullable = false)
    private LocalDateTime cmpUpdatedAt;

    @Builder.Default
    @OneToMany(mappedBy = "cfgComplianceCmp")
    private Set<CfgCompliance> cfgCompliances = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "brcCmp")
    private Set<ComBranch> comBranches = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "cusCmp")
    private Set<CusCustomer> cusCustomers = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "docCmp")
    private Set<DocDocument> docDocuments = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "finCoaCmp")
    private Set<FinCoa> finCoas = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "finConsRepCmp")
    private Set<FinConsReport> finConsReports = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "finJournalCmp")
    private Set<FinJournal> finJournals = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "finPeriodCmp")
    private Set<FinPeriod> finPeriods = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "hrCmp")
    private Set<HrWorker> hrWorkers = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "secusCmp")
    private Set<SecUser> secUsers = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "usrCmp")
    private Set<UsrUser> usrUsers = new LinkedHashSet<>();

}