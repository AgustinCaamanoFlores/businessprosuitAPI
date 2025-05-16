package com.businessprosuite.api.model.analytics;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "mat_view_sales", schema = "BusinessProSuite")
public class MatViewSale {
    @EmbeddedId
    private MatViewSaleId id;

    @NotNull
    @Column(name = "total_sales", nullable = false, precision = 18, scale = 2)
    private BigDecimal totalSales;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

}