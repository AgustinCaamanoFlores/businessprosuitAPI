package com.businessprosuite.api.model.inventory;

import com.businessprosuite.api.model.config.ConfigCompany;
import com.businessprosuite.api.model.finance.InvoiceDetail;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "inv_product", schema = "BusinessProSuite", indexes = {
        @Index(name = "idx_inv_prod_empresa_stock", columnList = "empresa_id, inv_prod_stock"),
        @Index(name = "inv_prod_cat_idx", columnList = "inv_prod_cat_id"),
        @Index(name = "inv_prod_sup_idx", columnList = "inv_prod_sup_id")
})
public class InventoryProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inv_prod_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ColumnDefault("1")
    @JoinColumn(name = "empresa_id", nullable = false)
    private ConfigCompany configCompany;

    @Size(max = 100)
    @NotNull
    @Column(name = "inv_prod_name", nullable = false, length = 100)
    private String invProdName;

    @Size(max = 100)
    @Column(name = "inv_prod_description", length = 100)
    private String invProdDescription;

    @NotNull
    @Column(name = "inv_prod_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal invProdPrice;

    @NotNull
    @Column(name = "inv_prod_stock", nullable = false)
    private Integer invProdStock;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "inv_prod_cat_id", nullable = false)
    private InventoryCategory invProdInventoryCategory;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "inv_prod_sup_id", nullable = false)
    private InventorySupplier invProdSup;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "inv_prod_created_at", nullable = false)
    private LocalDateTime invProdCreatedAt;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "inv_prod_updated_at", nullable = false)
    private LocalDateTime invProdUpdatedAt;

    @ColumnDefault("10")
    @Column(name = "inv_prod_reorder_level")
    private Integer invProdReorderLevel;

    @Builder.Default
    @OneToMany(mappedBy = "finInvdProd")
    private Set<InvoiceDetail> invoiceDetails = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "invItProd")
    private Set<InventoryTransaction> inventoryTransactions = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "invLotProd")
    private Set<InventoryLot> inventoryLots = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "prod")
    private Set<InventoryReorderRule> inventoryReorderRules = new LinkedHashSet<>();

}