package com.businessprosuite.api.model.inventory;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "inv_whse", schema = "BusinessProSuite")
public class InventoryWarehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inv_whse_id", nullable = false)
    private Integer id;

    @Size(max = 45)
    @NotNull
    @Column(name = "inv_whse_name", nullable = false, length = 45)
    private String invWhseName;

    @Size(max = 45)
    @NotNull
    @Column(name = "inv_whse_address", nullable = false, length = 45)
    private String invWhseAddress;

    @Size(max = 45)
    @NotNull
    @Column(name = "inv_whse_phone", nullable = false, length = 45)
    private String invWhsePhone;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "inv_whse_created_at", nullable = false)
    private LocalDateTime invWhseCreatedAt;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "inv_whse_updated_at", nullable = false)
    private LocalDateTime invWhseUpdatedAt;

    @Builder.Default
    @OneToMany(mappedBy = "whs")
    private Set<InventoryLotLocationHistory> invLotLocationHistories = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "invLotWhs")
    private Set<InventoryLot> inventoryLots = new LinkedHashSet<>();

}