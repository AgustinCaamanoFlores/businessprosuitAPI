package com.businessprosuite.api.model.subs;

import com.businessprosuite.api.model.customer.Customer;
import com.businessprosuite.api.model.config.ConfigCompany;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "subs_suscripcion", schema = "BusinessProSuite")
public class SubsSuscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subs_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subs_cus_id", nullable = false)
    private Customer subsCus;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subs_plan_id", nullable = false)
    private SubsPlan subsPlan;

    @NotNull
    @Column(name = "subs_fecha_inicio", nullable = false)
    private LocalDate subsFechaInicio;

    @NotNull
    @Column(name = "subs_prox_cobro", nullable = false)
    private LocalDate subsProxCobro;

    @NotNull
    @Lob
    @Column(name = "subs_estado", nullable = false)
    private String subsEstado;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ColumnDefault("1")
    @JoinColumn(name = "empresa_id", nullable = false)
    private ConfigCompany configCompany;

}