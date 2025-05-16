package com.businessprosuite.api.model.inventarios;

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
@Table(name = "inv_invtrans", schema = "BusinessProSuite", indexes = {
        @Index(name = "inv_it_prod_idx", columnList = "inv_it_prod_id")
})
public class InvInvtran {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inv_it_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "inv_it_prod_id", nullable = false)
    private InvProduct invItProd;

    @NotNull
    @Column(name = "inv_it_date", nullable = false)
    private LocalDateTime invItDate;

    @NotNull
    @Lob
    @Column(name = "it_type", nullable = false)
    private String itType;

    @NotNull
    @Column(name = "inv_it_quantity", nullable = false)
    private Integer invItQuantity;

    @Lob
    @Column(name = "inv_it_description")
    private String invItDescription;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "inv_it_created_at", nullable = false)
    private LocalDateTime invItCreatedAt;

}