package com.businessprosuite.api.model.finance;

import com.businessprosuite.api.model.security.SecurityUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "fin_wallet", schema = "BusinessProSuite", indexes = {
        @Index(name = "fin_wlt_user_idx", columnList = "fin_wlt_secus_id")
}, uniqueConstraints = {
        @UniqueConstraint(name = "fin_wlt_unique", columnNames = {"fin_wlt_secus_id", "fin_wlt_cur_code"})
})
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fin_wlt_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "fin_wlt_secus_id", nullable = false)
    private SecurityUser finWltSecus;

    @NotNull
    @ColumnDefault("0.00")
    @Column(name = "fin_wlt_balance", nullable = false, precision = 15, scale = 2)
    private BigDecimal finWltBalance;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ColumnDefault("'USD'")
    @JoinColumn(name = "fin_wlt_cur_code", nullable = false, referencedColumnName = "fin_cur_code")
    private Currency finWltCurCode;

    @NotNull
    @ColumnDefault("'Active'")
    @Lob
    @Column(name = "fin_wlt_status", nullable = false)
    private String finWltStatus;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "fin_wlt_created_at", nullable = false)
    private LocalDateTime finWltCreatedAt;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "fin_wlt_updated_at", nullable = false)
    private LocalDateTime finWltUpdatedAt;

}