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
@Table(name = "aud_activity_log", schema = "BusinessProSuite", indexes = {
        @Index(name = "al_user_idx", columnList = "al_user_id")
})
public class ActivityLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "al_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "al_user_id", nullable = false)
    private SecUser alUser;

    @Size(max = 100)
    @NotNull
    @Column(name = "al_action", nullable = false, length = 100)
    private String alAction;

    @Lob
    @Column(name = "al_description")
    private String alDescription;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "al_date", nullable = false)
    private LocalDateTime alDate;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "al_created_at", nullable = false)
    private LocalDateTime alCreatedAt;

}