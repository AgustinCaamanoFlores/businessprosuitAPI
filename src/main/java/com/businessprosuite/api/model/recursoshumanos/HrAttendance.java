package com.businessprosuite.api.model.recursoshumanos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "hr_attendance", schema = "BusinessProSuite")
public class HrAttendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hr_att_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hr_att_hr_w_id", nullable = false)
    private HrWorker hrAttHrW;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hr_att_hr_shift_id", nullable = false)
    private HrShift hrAttHrShift;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "hr_att_time", nullable = false)
    private LocalDateTime hrAttTime;

    @NotNull
    @ColumnDefault("'IN'")
    @Lob
    @Column(name = "hr_att_type", nullable = false)
    private String hrAttType;

    @ColumnDefault("'Fingerprint'")
    @Lob
    @Column(name = "hr_att_method")
    private String hrAttMethod;

    @Column(name = "hr_att_device_id")
    private Integer hrAttDeviceId;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "hr_att_created_at", nullable = false)
    private LocalDateTime hrAttCreatedAt;

}