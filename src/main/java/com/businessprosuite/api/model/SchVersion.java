package com.businessprosuite.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sch_version", schema = "BusinessProSuite")
public class SchVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sch_id", nullable = false)
    private Integer id;

    @Size(max = 150)
    @NotNull
    @Column(name = "sch_version", nullable = false, length = 150)
    private String schVersion;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "sch_applied_at", nullable = false)
    private LocalDateTime schAppliedAt;

}