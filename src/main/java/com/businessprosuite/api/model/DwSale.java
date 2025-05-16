package com.businessprosuite.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "dw_sales", schema = "BusinessProSuite")
public class DwSale {
    @EmbeddedId
    private DwSaleId id;

    @NotNull
    @Column(name = "ds_quantity", nullable = false)
    private Integer dsQuantity;

    @NotNull
    @Column(name = "ds_total", nullable = false, precision = 18, scale = 2)
    private BigDecimal dsTotal;

}