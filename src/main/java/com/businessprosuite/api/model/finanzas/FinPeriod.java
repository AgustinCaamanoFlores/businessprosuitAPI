package com.businessprosuite.api.model.finanzas;

import com.businessprosuite.api.model.company.ComCompany;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "fin_periods", schema = "BusinessProSuite", indexes = {
        @Index(name = "periods_cmp_idx", columnList = "fin_period_cmp_id")
})
public class FinPeriod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fin_period_id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "fin_period_name", nullable = false, length = 50)
    private String finPeriodName;

    @NotNull
    @Column(name = "fin_period_start", nullable = false)
    private LocalDate finPeriodStart;

    @NotNull
    @Column(name = "fin_period_end", nullable = false)
    private LocalDate finPeriodEnd;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "fin_period_is_closed", nullable = false)
    private Byte finPeriodIsClosed;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "fin_period_cmp_id", nullable = false)
    private ComCompany finPeriodCmp;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "fin_period_created_at", nullable = false)
    private LocalDateTime finPeriodCreatedAt;

}