package com.businessprosuite.api.model.inventarios;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "inv_reorder_rules", schema = "BusinessProSuite")
public class InvReorderRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rule_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "prod_id", nullable = false)
    private InvProduct prod;

    @NotNull
    @Column(name = "threshold", nullable = false)
    private Integer threshold;

    @NotNull
    @Column(name = "order_qty", nullable = false)
    private Integer orderQty;

    @Column(name = "last_run")
    private LocalDateTime lastRun;

}