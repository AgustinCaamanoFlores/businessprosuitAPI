package com.businessprosuite.api.model.hr;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "hr_dept", schema = "BusinessProSuite")
public class WorkerDepartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hr_dept_id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "hr_dept_name", nullable = false, length = 100)
    private String hrDeptName;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "hr_dept_created_at", nullable = false)
    private LocalDateTime hrDeptCreatedAt;

    @Builder.Default
    @OneToMany(mappedBy = "workerDepartment")
    private Set<Worker> workers = new LinkedHashSet<>();

}