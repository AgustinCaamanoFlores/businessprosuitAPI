package com.businessprosuite.api.model.hr;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "hr_payroll", schema = "BusinessProSuite")
public class Payroll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hr_pay_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hr_w_id", nullable = false)
    private Worker worker;

    @NotNull
    @Column(name = "base_salary", nullable = false, precision = 10, scale = 2)
    private BigDecimal baseSalary;

    @Column(name = "bonus", precision = 10, scale = 2)
    private BigDecimal bonus;

    @Column(name = "deductions", precision = 10, scale = 2)
    private BigDecimal deductions;

    @NotNull
    @Column(name = "pay_date", nullable = false)
    private LocalDate payDate;

    @Lob
    @Column(name = "remarks")
    private String remarks;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
