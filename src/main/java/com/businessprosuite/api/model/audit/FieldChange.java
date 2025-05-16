package com.businessprosuite.api.model.audit;

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
@Table(name = "aud_field_changes", schema = "BusinessProSuite")
public class FieldChange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "afc_id", nullable = false)
    private Integer id;

    @Size(max = 64)
    @NotNull
    @Column(name = "table_name", nullable = false, length = 64)
    private String tableName;

    @Size(max = 255)
    @NotNull
    @Column(name = "pk_value", nullable = false)
    private String pkValue;

    @Size(max = 64)
    @NotNull
    @Column(name = "field_name", nullable = false, length = 64)
    private String fieldName;

    @Lob
    @Column(name = "old_value")
    private String oldValue;

    @Lob
    @Column(name = "new_value")
    private String newValue;

    @Column(name = "changed_by")
    private Integer changedBy;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "changed_at")
    private LocalDateTime changedAt;

}