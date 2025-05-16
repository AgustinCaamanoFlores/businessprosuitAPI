package com.businessprosuite.api.model.analytics;

import com.businessprosuite.api.model.config.ConfigCompany;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "kpi_def", schema = "BusinessProSuite")
public class KpiDef {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kpi_id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "kpi_nombre", nullable = false, length = 100)
    private String kpiNombre;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ColumnDefault("1")
    @JoinColumn(name = "empresa_id", nullable = false)
    private ConfigCompany configCompany;

    @Builder.Default
    @OneToMany(mappedBy = "kpi")
    private Set<KpiValor> kpiValors = new LinkedHashSet<>();

}