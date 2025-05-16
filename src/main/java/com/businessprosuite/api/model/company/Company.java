package com.businessprosuite.api.model.company;

import com.businessprosuite.api.model.customer.Customer;
import com.businessprosuite.api.model.config.ConfigCompliance;
import com.businessprosuite.api.model.config.ConfigCompany;
import com.businessprosuite.api.model.config.ConfigCountry;
import com.businessprosuite.api.model.document.Document;
import com.businessprosuite.api.model.finance.FinanceCOA;
import com.businessprosuite.api.model.finance.FinanceConsReport;
import com.businessprosuite.api.model.finance.Journal;
import com.businessprosuite.api.model.finance.Period;
import com.businessprosuite.api.model.hr.Worker;
import com.businessprosuite.api.model.security.SecUser;
import com.businessprosuite.api.model.user.User;
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
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cmp_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ColumnDefault("1")
    @JoinColumn(name = "empresa_id", nullable = false)
    private ConfigCompany configCompany;

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
    private ConfigCountry cmpConfigCountryCodigo;

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
    private Set<ConfigCompliance> configCompliances = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "brcCmp")
    private Set<CompanyBranch> companyBranches = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "cusCmp")
    private Set<Customer> customers = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "docCmp")
    private Set<Document> documents = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "finCoaCmp")
    private Set<FinanceCOA> financeCOAS = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "finConsRepCmp")
    private Set<FinanceConsReport> financeConsReports = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "finJournalCmp")
    private Set<Journal> journals = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "finPeriodCmp")
    private Set<Period> periods = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "hrCmp")
    private Set<Worker> workers = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "secusCmp")
    private Set<SecUser> secUsers = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "usrCmp")
    private Set<User> users = new LinkedHashSet<>();

}