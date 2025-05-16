package com.businessprosuite.api.model.finanzas;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "fin_payment", schema = "BusinessProSuite", indexes = {
        @Index(name = "fin_pay_inv_idx", columnList = "fin_pay_inv_id"),
        @Index(name = "idx_pay_date", columnList = "fin_pay_date")
})
public class FinPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fin_pay_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fin_pay_inv_id", nullable = false)
    private FinInvoice finPayInv;

    @NotNull
    @Column(name = "fin_pay_date", nullable = false)
    private LocalDateTime finPayDate;

    @NotNull
    @Column(name = "fin_pay_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal finPayAmount;

    @NotNull
    @Lob
    @Column(name = "fin_pay_method", nullable = false)
    private String finPayMethod;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "fin_pay_created_at", nullable = false)
    private LocalDateTime finPayCreatedAt;

}