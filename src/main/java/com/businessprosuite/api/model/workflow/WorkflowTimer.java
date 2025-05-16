package com.businessprosuite.api.model.workflow;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "wf_timers", schema = "BusinessProSuite")
public class WorkflowTimer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timer_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "wf_ins_id", nullable = false)
    private WorkflowInstance wfIns;

    @NotNull
    @Column(name = "due_at", nullable = false)
    private LocalDateTime dueAt;

    @Size(max = 100)
    @Column(name = "action", length = 100)
    private String action;

    @ColumnDefault("0")
    @Column(name = "executed")
    private Boolean executed;

}