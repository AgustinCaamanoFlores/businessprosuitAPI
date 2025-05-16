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
@Table(name = "inv_supplier", schema = "BusinessProSuite")
public class InventorySupplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inv_sup_id", nullable = false)
    private Integer id;

    @Size(max = 45)
    @NotNull
    @Column(name = "inv_sup_name", nullable = false, length = 45)
    private String invSupName;

    @Size(max = 45)
    @NotNull
    @Column(name = "inv_sup_email", nullable = false, length = 45)
    private String invSupEmail;

    @Size(max = 45)
    @NotNull
    @Column(name = "inv_sup_phone", nullable = false, length = 45)
    private String invSupPhone;

    @Size(max = 45)
    @Column(name = "inv_sup_address", length = 45)
    private String invSupAddress;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "inv_sup_created_at", nullable = false)
    private LocalDateTime invSupCreatedAt;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "inv_sup_updated_at", nullable = false)
    private LocalDateTime invSupUpdatedAt;

    @Builder.Default
    @OneToMany(mappedBy = "invProdSup")
    private Set<InventoryProduct> inventoryProducts = new LinkedHashSet<>();

}