package com.businessprosuite.api.model.recursoshumanos;

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
@Table(name = "hr_leave_types", schema = "BusinessProSuite")
public class HrLeaveType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lt_id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @NotNull
    @Column(name = "max_days", nullable = false)
    private Integer maxDays;

    @Builder.Default
    @OneToMany(mappedBy = "lt")
    private Set<HrLeaveRequest> hrLeaveRequests = new LinkedHashSet<>();

}