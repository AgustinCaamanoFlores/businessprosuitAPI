package com.businessprosuite.api.model.finance;

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
@Table(name = "fin_currency", schema = "BusinessProSuite", uniqueConstraints = {
        @UniqueConstraint(name = "fin_cur_code_UNIQUE", columnNames = {"fin_cur_code"})
})
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fin_cur_id", nullable = false)
    private Integer id;

    @Size(max = 3)
    @NotNull
    @Column(name = "fin_cur_code", nullable = false, length = 3)
    private String finCurCode;

    @Size(max = 50)
    @NotNull
    @Column(name = "fin_cur_name", nullable = false, length = 50)
    private String finCurName;

    @NotNull
    @Column(name = "fin_cur_exchange_rate", nullable = false, precision = 10, scale = 4)
    private BigDecimal finCurExchangeRate;

    @NotNull
    @Column(name = "fin_cur_last_updated", nullable = false)
    private LocalDateTime finCurLastUpdated;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "fin_cur_created_at", nullable = false)
    private LocalDateTime finCurCreatedAt;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "fin_cur_updated_at", nullable = false)
    private LocalDateTime finCurUpdatedAt;

    @OneToMany(mappedBy = "finConvCurFrom")
    private Set<Conversion> finConvsFrom = new LinkedHashSet<>();

    @OneToMany(mappedBy = "finConvCurTo")
    private Set<Conversion> finConvsTo = new LinkedHashSet<>();

    @OneToMany(mappedBy = "finWltCurCode")
    private Set<Wallet> wallets = new LinkedHashSet<>();

}