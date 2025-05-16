package com.businessprosuite.api.model.inventarios;

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
@Table(name = "inv_cat", schema = "BusinessProSuite", uniqueConstraints = {
        @UniqueConstraint(name = "uq_inv_cat_parent_name", columnNames = {"inv_cat_parent_id", "inv_cat_name"})
})
public class InvCat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inv_cat_id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "inv_cat_name", nullable = false, length = 50)
    private String invCatName;

    @Size(max = 45)
    @Column(name = "inv_cat_description", length = 45)
    private String invCatDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inv_cat_parent_id")
    private InvCat invCatParent;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "inv_cat_created_at", nullable = false)
    private LocalDateTime invCatCreatedAt;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "inv_cat_updated_at", nullable = false)
    private LocalDateTime invCatUpdatedAt;

    @Builder.Default
    @OneToMany(mappedBy = "invCatParent")
    private Set<InvCat> invCats = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "invProdCat")
    private Set<InvProduct> invProducts = new LinkedHashSet<>();

}