package com.businessprosuite.api.model.finance;

import com.businessprosuite.api.model.company.Company;
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
@Table(name = "fin_ap", schema = "BusinessProSuite")
public class AccountsPayable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fin_ap_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fin_ap_cmp_id", nullable = false)
    private Company company;

    @NotNull
    @Column(name = "fin_ap_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "fin_ap_due")
    private LocalDateTime dueDate;

    @NotNull
    @Lob
    @Column(name = "fin_ap_status", nullable = false)
    private String status;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "fin_ap_created_at", nullable = false)
    private LocalDateTime createdAt;
}
