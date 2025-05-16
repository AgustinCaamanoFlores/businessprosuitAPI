package com.businessprosuite.api.model.finance;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "fin_journal_detail", schema = "BusinessProSuite", indexes = {
        @Index(name = "jdetail_journal_idx", columnList = "fin_journal_detail_journal_id"),
        @Index(name = "jdetail_coa_idx", columnList = "fin_journal_detail_coa_id")
})
public class JournalDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fin_journal_detail_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "fin_journal_detail_journal_id", nullable = false)
    private Journal journalDetailJournal;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fin_journal_detail_coa_id", nullable = false)
    private FinanceCOA finJournalDetailFinanceCOA;

    @ColumnDefault("0.00")
    @Column(name = "fin_journal_detail_debit", precision = 10, scale = 2)
    private BigDecimal finJournalDetailDebit;

    @ColumnDefault("0.00")
    @Column(name = "fin_journal_detail_credit", precision = 10, scale = 2)
    private BigDecimal finJournalDetailCredit;

    @Size(max = 255)
    @Column(name = "fin_journal_detail_description")
    private String finJournalDetailDescription;

}