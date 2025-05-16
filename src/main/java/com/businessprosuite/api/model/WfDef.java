package com.businessprosuite.api.model;

import com.businessprosuite.api.model.configuraciones.CfgEmpresa;
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
@Table(name = "wf_def", schema = "BusinessProSuite")
public class WfDef {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wf_id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "wf_nombre", nullable = false, length = 100)
    private String wfNombre;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ColumnDefault("1")
    @JoinColumn(name = "empresa_id", nullable = false)
    private CfgEmpresa empresa;

    @Builder.Default
    @OneToMany(mappedBy = "wf")
    private Set<com.businessprosuite.api.model.WfDefVersion> wfDefVersions = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "wf")
    private Set<com.businessprosuite.api.model.WfEstado> wfEstados = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "wf")
    private Set<com.businessprosuite.api.model.WfInstancia> wfInstancias = new LinkedHashSet<>();

}