package com.businessprosuite.api.model.inventory;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import com.businessprosuite.api.model.inventory.InventoryWarehouse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "inv_lots", schema = "BusinessProSuite")
public class InventoryLot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inv_lot_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "inv_lot_prod_id", nullable = false)
    private InventoryProduct invLotProd;

    @Size(max = 50)
    @NotNull
    @Column(name = "inv_lot_number", nullable = false, length = 50)
    private String invLotNumber;

    @Column(name = "inv_lot_expiration_date")
    private LocalDate invLotExpirationDate;

    @NotNull
    @Column(name = "inv_lot_quantity", nullable = false)
    private Integer invLotQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "inv_lot_whs_id")
    private InventoryWarehouse invLotWhs;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "inv_lot_created_at", nullable = false)
    private LocalDateTime invLotCreatedAt;

    @Builder.Default
    @OneToMany(mappedBy = "inventoryLot")
    private Set<InventoryLotLocationHistory> invLotLocationHistories = new LinkedHashSet<>();

}