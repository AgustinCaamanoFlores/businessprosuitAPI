package com.businessprosuite.api.model.workflow;

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
@Table(name = "wf_def", schema = "BusinessProSuite")
public class WorkflowDefinition {
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
    private ConfigCompany configCompany;

    @Builder.Default
    @OneToMany(mappedBy = "wf")
    private Set<WorkflowDefinitionVersion> workflowDefinitionVersions = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "wf")
    private Set<WorkflowState> workflowStates = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "wf")
    private Set<WorkflowInstance> workflowInstances = new LinkedHashSet<>();

}