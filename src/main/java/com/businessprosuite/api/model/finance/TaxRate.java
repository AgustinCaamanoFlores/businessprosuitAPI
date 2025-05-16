package com.businessprosuite.api.model.finance;

import com.businessprosuite.api.model.config.ConfigCountry;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "fin_tax_rates", schema = "BusinessProSuite")
public class TaxRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tax_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "country_code", nullable = false)
    private ConfigCountry countryCode;

    @NotNull
    @Column(name = "valid_from", nullable = false)
    private LocalDate validFrom;

    @NotNull
    @Column(name = "rate", nullable = false, precision = 5, scale = 4)
    private BigDecimal rate;

}