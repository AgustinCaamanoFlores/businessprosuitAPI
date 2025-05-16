package com.businessprosuite.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sys_metrics", schema = "BusinessProSuite")
public class Metric {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "metric_id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "value_num", precision = 18, scale = 4)
    private BigDecimal valueNum;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "recorded_at", nullable = false)
    private LocalDateTime recordedAt;

}