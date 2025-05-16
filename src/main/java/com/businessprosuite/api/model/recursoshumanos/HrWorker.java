package com.businessprosuite.api.model.recursoshumanos;

import com.businessprosuite.api.model.company.ComCompany;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "hr_worker", schema = "BusinessProSuite", indexes = {
        @Index(name = "hr_worker_dept_idx", columnList = "hr_dept_id"),
        @Index(name = "hr_worker_shift_idx", columnList = "hr_shift_id"),
        @Index(name = "hr_worker_company_idx", columnList = "hr_cmp_id")
}, uniqueConstraints = {
        @UniqueConstraint(name = "hr_w_cell_UNIQUE", columnNames = {"hr_w_cell"}),
        @UniqueConstraint(name = "hr_w_email_UNIQUE", columnNames = {"hr_w_email"})
})
public class HrWorker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hr_w_id", nullable = false)
    private Integer id;

    @Size(max = 45)
    @NotNull
    @Column(name = "hr_w_name", nullable = false, length = 45)
    private String hrWName;

    @Size(max = 100)
    @NotNull
    @Column(name = "hr_w_address", nullable = false, length = 100)
    private String hrWAddress;

    @Size(max = 100)
    @NotNull
    @Column(name = "hr_w_cell", nullable = false, length = 100)
    private String hrWCell;

    @Size(max = 100)
    @NotNull
    @Column(name = "hr_w_home_phone", nullable = false, length = 100)
    private String hrWHomePhone;

    @Size(max = 100)
    @NotNull
    @Column(name = "hr_w_email", nullable = false, length = 100)
    private String hrWEmail;

    @NotNull
    @Column(name = "hr_w_birth_date", nullable = false)
    private LocalDate hrWBirthDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hr_dept_id", nullable = false)
    private HrDept hrDept;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hr_shift_id", nullable = false)
    private HrShift hrShift;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "hr_cmp_id", nullable = false)
    private ComCompany hrCmp;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "hr_w_deleted", nullable = false)
    private Byte hrWDeleted;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "hr_created_at", nullable = false)
    private LocalDateTime hrCreatedAt;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "hr_updated_at", nullable = false)
    private LocalDateTime hrUpdatedAt;

    @Builder.Default
    @OneToMany(mappedBy = "hrAttHrW")
    private Set<HrAttendance> hrAttendances = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "hrW")
    private Set<HrLeaveRequest> hrLeaveRequests = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "hrW")
    private Set<HrPerformance> hrPerformances = new LinkedHashSet<>();

}