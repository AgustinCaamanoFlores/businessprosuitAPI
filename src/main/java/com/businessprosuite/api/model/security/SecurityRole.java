package com.businessprosuite.api.model.security;

import com.businessprosuite.api.model.workflow.WorkflowAssignmentRule;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sec_roles", schema = "BusinessProSuite", uniqueConstraints = {
        @UniqueConstraint(name = "secrl_name_UNIQUE", columnNames = {"secrl_name"})
})
public class SecurityRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "secrl_id", nullable = false)
    private Integer id;

    @Size(max = 45)
    @NotNull
    @Column(name = "secrl_name", nullable = false, length = 45)
    private String secrlName;

    @Size(max = 255)
    @NotNull
    @Column(name = "secrl_description", nullable = false)
    private String secrlDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "secrl_parent_role_id")
    private SecurityRole secrlParentRole;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "secrl_created_at", nullable = false)
    private LocalDateTime secrlCreatedAt;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "secrl_updated_at", nullable = false)
    private LocalDateTime secrlUpdatedAt;

    @Builder.Default
    @OneToMany(mappedBy = "secRpSecrl")
    private Set<SecurityRolePerm> securityRolePerms = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "secrlParentRole")
    private Set<SecurityRole> securityRoles = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "secUrSecrl")
    private Set<SecurityUserRole> securityUserRoles = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "secusRole")
    private Set<SecurityUser> securityUsers = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "targetRole")
    private Set<WorkflowAssignmentRule> workflowAssignmentRules = new LinkedHashSet<>();

}