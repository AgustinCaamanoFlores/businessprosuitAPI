package com.businessprosuite.api.model.document;

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
@Table(name = "doc_documents", schema = "BusinessProSuite", indexes = {
        @Index(name = "doc_cmp_idx", columnList = "doc_cmp_id")
})
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doc_id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "doc_name", nullable = false, length = 100)
    private String docName;

    @Size(max = 50)
    @NotNull
    @Column(name = "doc_type", nullable = false, length = 50)
    private String docType;

    @Size(max = 255)
    @Column(name = "doc_url")
    private String docUrl;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "doc_cmp_id", nullable = false)
    private Company docCmp;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "doc_created_at", nullable = false)
    private LocalDateTime docCreatedAt;

    @Builder.Default
    @OneToMany(mappedBy = "doc")
    private Set<DocumentVersion> documentVersions = new LinkedHashSet<>();

}