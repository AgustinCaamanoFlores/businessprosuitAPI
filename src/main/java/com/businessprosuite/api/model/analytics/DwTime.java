package com.businessprosuite.api.model.analytics;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "dw_time", schema = "BusinessProSuite")
public class DwTime {
    @Id
    @Column(name = "dt_date", nullable = false)
    private LocalDate id;

    @Column(name = "dt_day")
    private Byte dtDay;

    @Column(name = "dt_month")
    private Byte dtMonth;

    @Column(name = "dt_year")
    private Integer dtYear;

}