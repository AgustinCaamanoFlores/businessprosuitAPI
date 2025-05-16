package com.businessprosuite.api.model.workflow;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "wf_estado", schema = "BusinessProSuite")
public class WorkflowState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wf_est_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wf_id", nullable = false)
    private WorkflowDefinition wf;

    @Size(max = 50)
    @NotNull
    @Column(name = "wf_est_nombre", nullable = false, length = 50)
    private String wfEstNombre;

    @NotNull
    @Column(name = "wf_est_orden", nullable = false)
    private Integer wfEstOrden;

    @Builder.Default
    @OneToMany(mappedBy = "wfEst")
    private Set<WorkflowInstance> workflowInstances = new LinkedHashSet<>();

}