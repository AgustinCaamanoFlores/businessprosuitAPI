package com.businessprosuite.api.model;

import com.businessprosuite.api.model.security.SecRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "wf_assignment_rules", schema = "BusinessProSuite")
public class WfAssignmentRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ar_id", nullable = false)
    private Integer id;

    @NotNull
    @Lob
    @Column(name = "expr", nullable = false)
    private String expr;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "target_role", nullable = false)
    private SecRole targetRole;

}