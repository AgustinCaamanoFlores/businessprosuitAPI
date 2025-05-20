package com.businessprosuite.api.model.audit;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditTrailId implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "auda_table", length = 100, nullable = false)
    private String audaTable;

    @NotNull
    @Column(name = "auda_record_id", nullable = false)
    private Integer audaRecordId;

    @NotNull
    @Column(name = "auda_changed_at", nullable = false)
    private LocalDateTime audaChangedAt;

}
