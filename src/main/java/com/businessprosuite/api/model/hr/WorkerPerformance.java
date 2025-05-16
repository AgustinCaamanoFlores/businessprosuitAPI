package com.businessprosuite.api.model.hr;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "hr_performance", schema = "BusinessProSuite")
public class WorkerPerformance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "perf_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hr_w_id", nullable = false)
    private Worker hrW;

    @NotNull
    @Column(name = "eval_date", nullable = false)
    private LocalDate evalDate;

    @NotNull
    @Column(name = "score", nullable = false, precision = 5, scale = 2)
    private BigDecimal score;

    @Lob
    @Column(name = "remarks")
    private String remarks;

}