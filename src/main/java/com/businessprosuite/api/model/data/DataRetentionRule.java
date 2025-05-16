package com.businessprosuite.api.model.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "data_retention_rules", schema = "BusinessProSuite")
public class DataRetentionRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rule_id", nullable = false)
    private Integer id;

    @Size(max = 64)
    @NotNull
    @Column(name = "table_name", nullable = false, length = 64)
    private String tableName;

    @NotNull
    @Column(name = "keep_months", nullable = false)
    private Integer keepMonths;

    @Column(name = "last_run")
    private LocalDateTime lastRun;

}