package com.businessprosuite.api.model.cliente;

import com.businessprosuite.api.model.company.ComCompany;
import com.businessprosuite.api.model.configuraciones.CfgEmpresa;
import com.businessprosuite.api.model.configuraciones.CfgPai;
import com.businessprosuite.api.model.finanzas.FinInvoice;
import com.businessprosuite.api.model.leasing.LeasingContrato;
import com.businessprosuite.api.model.security.SubsSuscripcion;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cus_customer", schema = "BusinessProSuite", indexes = {
        @Index(name = "cus_cmp_idx", columnList = "cus_cmp_id")
}, uniqueConstraints = {
        @UniqueConstraint(name = "cus_email_UNIQUE", columnNames = {"cus_email"})
})
public class CusCustomer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cus_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ColumnDefault("1")
    @JoinColumn(name = "empresa_id", nullable = false)
    private CfgEmpresa empresa;

    @Size(max = 100)
    @NotNull
    @Column(name = "cus_name", nullable = false, length = 100)
    private String cusName;

    @Size(max = 100)
    @NotNull
    @Column(name = "cus_email", nullable = false, length = 100)
    private String cusEmail;

    @Size(max = 100)
    @NotNull
    @Column(name = "cus_phone", nullable = false, length = 100)
    private String cusPhone;

    @Size(max = 100)
    @NotNull
    @Column(name = "cus_address", nullable = false, length = 100)
    private String cusAddress;

    @Size(max = 50)
    @Column(name = "cus_tax_id", length = 50)
    private String cusTaxId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "cus_cmp_id", nullable = false)
    private ComCompany cusCmp;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cus_pais_codigo", nullable = false)
    private CfgPai cusPaisCodigo;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "cus_reg_date", nullable = false)
    private LocalDateTime cusRegDate;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "cus_created_at", nullable = false)
    private LocalDateTime cusCreatedAt;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "cus_updated_at", nullable = false)
    private LocalDateTime cusUpdatedAt;

    @Builder.Default
    @OneToMany(mappedBy = "finInvCus")
    private Set<FinInvoice> finInvoices = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "lcCus")
    private Set<LeasingContrato> leasingContratoes = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "subsCus")
    private Set<SubsSuscripcion> subsSuscripcions = new LinkedHashSet<>();

}