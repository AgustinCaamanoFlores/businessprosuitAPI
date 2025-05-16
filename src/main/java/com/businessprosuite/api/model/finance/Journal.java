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
@Table(name = "fin_journal", schema = "BusinessProSuite", indexes = {
        @Index(name = "fin_journal_cmp_idx", columnList = "fin_journal_cmp_id")
})
public class Journal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fin_journal_id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "fin_journal_date", nullable = false)
    private LocalDateTime finJournalDate;

    @Size(max = 255)
    @Column(name = "fin_journal_description")
    private String finJournalDescription;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "fin_journal_cmp_id", nullable = false)
    private Company finJournalCmp;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "fin_journal_created_at", nullable = false)
    private LocalDateTime finJournalCreatedAt;

    @Builder.Default
    @OneToMany(mappedBy = "journalDetailJournal")
    private Set<JournalDetail> journalDetails = new LinkedHashSet<>();

}