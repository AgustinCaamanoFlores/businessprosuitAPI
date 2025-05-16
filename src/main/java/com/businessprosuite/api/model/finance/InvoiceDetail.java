package com.businessprosuite.api.model.finance;

import com.businessprosuite.api.model.inventory.InventoryProduct;
import jakarta.persistence.*;
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
@Table(name = "fin_invoice_detail", schema = "BusinessProSuite", indexes = {
        @Index(name = "fin_invd_inv_idx", columnList = "fin_invd_inv_id"),
        @Index(name = "fin_invd_prod_idx", columnList = "fin_invd_prod_id")
})
public class InvoiceDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fin_invd_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fin_invd_inv_id", nullable = false)
    private Invoice finInvdInv;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fin_invd_prod_id", nullable = false)
    private InventoryProduct finInvdProd;

    @NotNull
    @Column(name = "fin_invd_quantity", nullable = false)
    private Integer finInvdQuantity;

    @NotNull
    @Column(name = "fin_invd_unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal finInvdUnitPrice;

    @NotNull
    @Column(name = "fin_invd_total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal finInvdTotalPrice;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "fin_invd_created_at", nullable = false)
    private LocalDateTime finInvdCreatedAt;

}