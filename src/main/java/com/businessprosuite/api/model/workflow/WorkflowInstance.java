package com.businessprosuite.api.model.workflow;

import com.businessprosuite.api.model.security.SecUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "wf_instancia", schema = "BusinessProSuite")
public class WorkflowInstance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wf_ins_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wf_id", nullable = false)
    private WorkflowDefinition wf;

    @Size(max = 50)
    @Column(name = "wf_entidad_tipo", length = 50)
    private String wfEntidadTipo;

    @Column(name = "wf_entidad_id")
    private Integer wfEntidadId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wf_est_id", nullable = false)
    private WorkflowState wfEst;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wf_asignado_a")
    private SecUser wfAsignadoA;

    @Column(name = "wf_fecha_inicio")
    private LocalDateTime wfFechaInicio;

    @Builder.Default
    @OneToMany(mappedBy = "wfIns")
    private Set<WorkflowHistory> workflowHistories = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "wfIns")
    private Set<WorkflowTimer> workflowTimers = new LinkedHashSet<>();

}