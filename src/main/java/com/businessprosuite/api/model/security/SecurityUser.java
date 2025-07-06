package com.businessprosuite.api.model.security;

import com.businessprosuite.api.model.gdpr.GDPRRequest;
import com.businessprosuite.api.model.notification.NotificationQueue;
import com.businessprosuite.api.model.audit.ActivityLog;
import com.businessprosuite.api.model.audit.LoginAttempt;
import com.businessprosuite.api.model.audit.SensitiveDataAccess;
import com.businessprosuite.api.model.company.Company;
import com.businessprosuite.api.model.finance.Invoice;
import com.businessprosuite.api.model.finance.Wallet;
import com.businessprosuite.api.model.workflow.WorkflowInstance;
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
@Table(name = "sec_users", schema = "BusinessProSuite", indexes = {
        @Index(name = "secus_role_idx", columnList = "secus_role_id"),
        @Index(name = "secus_company_idx", columnList = "secus_cmp_id")
}, uniqueConstraints = {
        @UniqueConstraint(name = "secus_name_UNIQUE", columnNames = {"secus_name"}),
        @UniqueConstraint(name = "secus_email_UNIQUE", columnNames = {"secus_email"})
})
public class SecurityUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "secus_id", nullable = false)
    private Integer id;

    @Size(max = 45)
    @NotNull
    @Column(name = "secus_name", nullable = false, length = 45)
    private String secusName;

    @Size(max = 255)
    @NotNull
    @Column(name = "secus_password", nullable = false)
    private String secusPassword;

    @Size(max = 45)
    @NotNull
    @Column(name = "secus_email", nullable = false, length = 45)
    private String secusEmail;

    @NotNull
    @Column(name = "secus_available", nullable = false)
    private Byte secusAvailable;

    @Column(name = "secus_last_login")
    private LocalDateTime secusLastLogin;

    @NotNull
    @Column(name = "secus_active", nullable = false)
    private Byte secusActive;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "secus_mfa_enabled", nullable = false)
    private Byte secusMfaEnabled;

    @Size(max = 255)
    @Column(name = "secus_mfa_secret")
    private String secusMfaSecret;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "secus_role_id", nullable = false)
    private SecurityRole secusRole;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "secus_cmp_id", nullable = false)
    private Company secusCmp;

    @Column(name = "secus_last_password_change")
    private LocalDateTime secusLastPasswordChange;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "secus_failed_attempts", nullable = false)
    private Integer secusFailedAttempts;

    @Size(max = 2)
    @Column(name = "secus_residence", length = 2)
    private String secusResidence;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "secus_created_at", nullable = false)
    private LocalDateTime secusCreatedAt;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "secus_updated_at", nullable = false)
    private LocalDateTime secusUpdatedAt;

    @Builder.Default
    @OneToMany(mappedBy = "alUser")
    private Set<ActivityLog> activityLogs = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "aulaUser")
    private Set<LoginAttempt> loginAttempts = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "sdaUser")
    private Set<SensitiveDataAccess> sensitiveDataAccesses = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "finInvSecus")
    private Set<Invoice> invoices = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "finWltSecus")
    private Set<Wallet> wallets = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "secus")
    private Set<GDPRRequest> GDPRRequests = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "secus")
    private Set<NotificationQueue> notificationQueues = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "secus")
    private Set<SecurityOtp> securityOtps = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "secphSecus")
    private Set<SecurityPasswordHistory> secPasswordHistories = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "secus")
    private Set<SecuritySession> securitySessions = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "secUrSecus")
    private Set<SecurityUserRole> securityUserRoles = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "wfAsignadoA")
    private Set<WorkflowInstance> workflowInstances = new LinkedHashSet<>();

}