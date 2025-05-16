package com.businessprosuite.api.model.audit;

import com.businessprosuite.api.model.security.SecUser;
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
@Table(name = "aud_sensitive_data_access", schema = "BusinessProSuite")
public class SensitiveDataAccess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sda_access_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sda_user_id", nullable = false)
    private SecUser sdaUser;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "sda_accessed_at")
    private LocalDateTime sdaAccessedAt;

    @Size(max = 100)
    @Column(name = "sda_accessed_field", length = 100)
    private String sdaAccessedField;

    @Size(max = 255)
    @Column(name = "sda_access_reason")
    private String sdaAccessReason;

}