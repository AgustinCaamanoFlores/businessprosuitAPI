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
@Table(name = "aud_audit_trail", schema = "BusinessProSuite")
public class AuditTrail {
    @EmbeddedId
    private AuditTrailId id;

    @Size(max = 100)
    @NotNull
    @Column(name = "auda_table", nullable = false, length = 100, insertable = false, updatable = false)
    private String audaTable;

    @NotNull
    @Column(name = "auda_record_id", nullable = false, insertable = false, updatable = false)
    private Integer audaRecordId;

    @NotNull
    @Lob
    @Column(name = "auda_action", nullable = false)
    private String audaAction;

    @Lob
    @Column(name = "auda_changed_data")
    private String audaChangedData;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "auda_changed_at", nullable = false, insertable = false, updatable = false)
    private LocalDateTime audaChangedAt;

    @Size(max = 100)
    @Column(name = "auda_ip", length = 100)
    private String audaIp;

    @Column(name = "auda_user_id")
    private Integer audaUserId;

    @Size(max = 255)
    @Column(name = "auda_user_agent")
    private String audaUserAgent;

    @Size(max = 2)
    @Column(name = "auda_country", length = 2)
    private String audaCountry;

}