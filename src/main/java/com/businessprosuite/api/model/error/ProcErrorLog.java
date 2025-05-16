package com.businessprosuite.api.model.error;

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
@Table(name = "proc_error_log", schema = "BusinessProSuite")
public class ProcErrorLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "proc_error_id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "procedure_name", nullable = false, length = 100)
    private String procedureName;

    @Size(max = 255)
    @NotNull
    @Column(name = "error_message", nullable = false)
    private String errorMessage;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "error_timestamp", nullable = false)
    private LocalDateTime errorTimestamp;

}