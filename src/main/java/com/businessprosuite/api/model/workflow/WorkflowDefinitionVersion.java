package com.businessprosuite.api.model.workflow;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "wf_def_version", schema = "BusinessProSuite")
public class WorkflowDefinitionVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ver_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "wf_id", nullable = false)
    private WorkflowDefinition wf;

    @NotNull
    @Lob
    @Column(name = "snapshot_json", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private String snapshotJson;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "effective_from", nullable = false)
    private LocalDateTime effectiveFrom;

}