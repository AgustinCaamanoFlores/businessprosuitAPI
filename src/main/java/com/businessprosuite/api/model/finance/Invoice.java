package com.businessprosuite.api.model.finance;

import com.businessprosuite.api.model.customer.Customer;
import com.businessprosuite.api.model.config.ConfigCompany;
import com.businessprosuite.api.model.security.SecurityUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "fin_invoice", schema = "BusinessProSuite", indexes = {
        @Index(name = "idx_fin_emp_date", columnList = "empresa_id, fin_inv_date"),
        @Index(name = "idx_fin_inv_date", columnList = "fin_inv_date"),
        @Index(name = "fin_inv_cus_idx", columnList = "fin_inv_cus_id"),
        @Index(name = "fin_inv_user_idx", columnList = "fin_inv_secus_id")
})
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fin_inv_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ColumnDefault("1")
    @JoinColumn(name = "empresa_id", nullable = false)
    private ConfigCompany configCompany;

    @NotNull
    @Column(name = "fin_inv_date", nullable = false)
    private LocalDateTime finInvDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fin_inv_cus_id", nullable = false)
    private Customer finInvCus;

    @NotNull
    @Column(name = "fin_inv_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal finInvTotal;

    @NotNull
    @Column(name = "fin_inv_tax", nullable = false, precision = 10, scale = 2)
    private BigDecimal finInvTax;

    @NotNull
    @Column(name = "fin_inv_discount", nullable = false, precision = 10, scale = 2)
    private BigDecimal finInvDiscount;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fin_inv_secus_id", nullable = false)
    private SecurityUser finInvSecus;

    @NotNull
    @Lob
    @Column(name = "fin_inv_payment_status", nullable = false)
    private String finInvPaymentStatus;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "fin_inv_created_at", nullable = false)
    private LocalDateTime finInvCreatedAt;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "fin_inv_updated_at", nullable = false)
    private LocalDateTime finInvUpdatedAt;

    @ColumnDefault("((`fin_inv_total` - `fin_inv_discount`) + `fin_inv_tax`)")
    @Column(name = "fin_inv_net", precision = 10, scale = 2)
    private BigDecimal finInvNet;

    @Builder.Default
    @OneToMany(mappedBy = "finInvdInv")
    private Set<InvoiceDetail> invoiceDetails = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "finPayInv")
    private Set<Payment> payments = new LinkedHashSet<>();

}