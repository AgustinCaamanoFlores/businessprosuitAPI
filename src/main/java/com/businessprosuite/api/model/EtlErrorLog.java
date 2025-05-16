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
@Table(name = "etl_error_log", schema = "BusinessProSuite")
public class EtlErrorLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "error_id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "error_message", nullable = false)
    private String errorMessage;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "error_date", nullable = false)
    private LocalDateTime errorDate;

}