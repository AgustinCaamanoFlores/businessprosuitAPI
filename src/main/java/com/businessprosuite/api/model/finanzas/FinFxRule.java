package com.businessprosuite.api.model.finanzas;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "fin_fx_rules", schema = "BusinessProSuite")
public class FinFxRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rule_id", nullable = false)
    private Integer id;

    @Size(max = 3)
    @NotNull
    @Column(name = "from_code", nullable = false, length = 3)
    private String fromCode;

    @Size(max = 3)
    @NotNull
    @Column(name = "to_code", nullable = false, length = 3)
    private String toCode;

    @NotNull
    @ColumnDefault("0.0000")
    @Column(name = "spread_pct", nullable = false, precision = 5, scale = 4)
    private BigDecimal spreadPct;

    @Size(max = 20)
    @NotNull
    @ColumnDefault("'NONE'")
    @Column(name = "rounding", nullable = false, length = 20)
    private String rounding;

}