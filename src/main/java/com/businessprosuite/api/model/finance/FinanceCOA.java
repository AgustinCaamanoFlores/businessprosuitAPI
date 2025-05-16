package com.businessprosuite.api.model.finance;

import com.businessprosuite.api.model.company.Company;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "fin_coa", schema = "BusinessProSuite", indexes = {
        @Index(name = "fin_coa_cmp_idx", columnList = "fin_coa_cmp_id")
}, uniqueConstraints = {
        @UniqueConstraint(name = "fin_coa_code_UNIQUE", columnNames = {"fin_coa_code"})
})
public class FinanceCOA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fin_coa_id", nullable = false)
    private Integer id;

    @Size(max = 20)
    @NotNull
    @Column(name = "fin_coa_code", nullable = false, length = 20)
    private String finCoaCode;

    @Size(max = 100)
    @NotNull
    @Column(name = "fin_coa_name", nullable = false, length = 100)
    private String finCoaName;

    @NotNull
    @Lob
    @Column(name = "fin_coa_type", nullable = false)
    private String finCoaType;

    @Size(max = 255)
    @Column(name = "fin_coa_description")
    private String finCoaDescription;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "fin_coa_cmp_id", nullable = false)
    private Company finCoaCmp;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "fin_coa_created_at", nullable = false)
    private LocalDateTime finCoaCreatedAt;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "fin_coa_updated_at", nullable = false)
    private LocalDateTime finCoaUpdatedAt;

    @Builder.Default
    @OneToMany(mappedBy = "finJournalDetailFinanceCOA")
    private Set<JournalDetail> journalDetails = new LinkedHashSet<>();

}