package com.businessprosuite.api.model.recursoshumanos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "hr_shift", schema = "BusinessProSuite")
public class HrShift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hr_shift_id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "hr_shift_name", nullable = false, length = 50)
    private String hrShiftName;

    @NotNull
    @Column(name = "hr_shift_start_time", nullable = false)
    private LocalTime hrShiftStartTime;

    @NotNull
    @Column(name = "hr_shift_end_time", nullable = false)
    private LocalTime hrShiftEndTime;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "hr_shift_created_at", nullable = false)
    private LocalDateTime hrShiftCreatedAt;

    @Builder.Default
    @OneToMany(mappedBy = "hrAttHrShift")
    private Set<HrAttendance> hrAttendances = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "hrShift")
    private Set<HrWorker> hrWorkers = new LinkedHashSet<>();

}