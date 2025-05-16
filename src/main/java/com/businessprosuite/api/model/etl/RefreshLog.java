package com.businessprosuite.api.model.etl;

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
@Table(name = "etl_refresh_log", schema = "BusinessProSuite")
public class RefreshLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "process_name", nullable = false, length = 100)
    private String processName;

    @NotNull
    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    @Column(name = "finished_at")
    private LocalDateTime finishedAt;

    @Column(name = "rows_processed")
    private Integer rowsProcessed;

    @NotNull
    @Lob
    @Column(name = "status", nullable = false)
    private String status;

    @Lob
    @Column(name = "message")
    private String message;

}