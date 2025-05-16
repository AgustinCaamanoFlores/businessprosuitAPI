package com.businessprosuite.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sch_changelog", schema = "BusinessProSuite")
public class SchChangelog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sch_change_id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "sch_change_description", nullable = false)
    private String schChangeDescription;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "sch_change_date", nullable = false)
    private LocalDateTime schChangeDate;

    @Size(max = 100)
    @Column(name = "sch_applied_by", length = 100)
    private String schAppliedBy;

    @Size(max = 50)
    @ColumnDefault("'DML'")
    @Column(name = "sch_change_type", length = 50)
    private String schChangeType;

    @Size(max = 100)
    @ColumnDefault("'Unknown'")
    @Column(name = "sch_author", length = 100)
    private String schAuthor;

    @Lob
    @Column(name = "sch_comments")
    private String schComments;

}