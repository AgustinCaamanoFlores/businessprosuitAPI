package com.businessprosuite.api.model.inventory;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "inv_lot_location_history", schema = "BusinessProSuite")
public class InventoryLotLocationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hist_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "inv_lot_id", nullable = false)
    private InventoryLot inventoryLot;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "whs_id", nullable = false)
    private InventoryWerehouse whs;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "moved_at", nullable = false)
    private LocalDateTime movedAt;

}