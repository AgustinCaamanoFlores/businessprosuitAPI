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
@Table(name = "test_results", schema = "BusinessProSuite")
public class TestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "test_name", nullable = false, length = 100)
    private String testName;

    @Size(max = 50)
    @Column(name = "test_result", length = 50)
    private String testResult;

    @Lob
    @Column(name = "test_message")
    private String testMessage;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "test_date", nullable = false)
    private LocalDateTime testDate;

}