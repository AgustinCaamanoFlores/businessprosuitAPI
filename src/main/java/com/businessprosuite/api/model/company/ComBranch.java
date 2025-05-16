package com.businessprosuite.api.model.company;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "com_branch", schema = "BusinessProSuite", uniqueConstraints = {
        @UniqueConstraint(name = "brc_name_UNIQUE", columnNames = {"brc_name"})
})
public class ComBranch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brc_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "brc_cmp_id", nullable = false)
    private ComCompany brcCmp;

    @Size(max = 100)
    @NotNull
    @Column(name = "brc_name", nullable = false, length = 100)
    private String brcName;

    @Size(max = 200)
    @NotNull
    @Column(name = "brc_address", nullable = false, length = 200)
    private String brcAddress;

    @Size(max = 50)
    @Column(name = "brc_phone", length = 50)
    private String brcPhone;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "brc_created_at", nullable = false)
    private LocalDateTime brcCreatedAt;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "brc_updated_at", nullable = false)
    private LocalDateTime brcUpdatedAt;

}