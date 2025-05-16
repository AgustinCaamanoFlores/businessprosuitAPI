package com.businessprosuite.api.model.leasing;

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
@Table(name = "leasing_pago", schema = "BusinessProSuite")
public class LeasingPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lp_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lc_id", nullable = false)
    private LeasingContract lc;

    @NotNull
    @Column(name = "lp_fecha_venc", nullable = false)
    private LocalDate lpFechaVenc;

    @NotNull
    @Column(name = "lp_monto", nullable = false, precision = 18, scale = 2)
    private BigDecimal lpMonto;

    @NotNull
    @Lob
    @Column(name = "lp_status", nullable = false)
    private String lpStatus;

}