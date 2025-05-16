package com.businessprosuite.api.model.finance;

import com.businessprosuite.api.model.company.Company;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "fin_cons_reports", schema = "BusinessProSuite")
public class FinanceConsReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fin_cons_rep_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "fin_cons_rep_cmp_id", nullable = false)
    private Company finConsRepCmp;

    @NotNull
    @Column(name = "fin_cons_rep_date", nullable = false)
    private LocalDate finConsRepDate;

    @NotNull
    @Column(name = "fin_cons_rep_total_assets", nullable = false, precision = 18, scale = 2)
    private BigDecimal finConsRepTotalAssets;

    @NotNull
    @Column(name = "fin_cons_rep_total_liabilities", nullable = false, precision = 18, scale = 2)
    private BigDecimal finConsRepTotalLiabilities;

    @NotNull
    @Column(name = "fin_cons_rep_net_income", nullable = false, precision = 18, scale = 2)
    private BigDecimal finConsRepNetIncome;

    @Size(max = 3)
    @NotNull
    @Column(name = "fin_cons_rep_currency", nullable = false, length = 3)
    private String finConsRepCurrency;

    @ColumnDefault("1.0000")
    @Column(name = "fin_cons_rep_conversion_factor", precision = 10, scale = 4)
    private BigDecimal finConsRepConversionFactor;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "fin_cons_rep_created_at", nullable = false)
    private LocalDateTime finConsRepCreatedAt;

}