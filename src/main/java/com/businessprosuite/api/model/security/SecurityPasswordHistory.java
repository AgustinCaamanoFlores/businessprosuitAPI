package com.businessprosuite.api.model.security;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.OffsetDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sec_password_history", schema = "BusinessProSuite")
public class SecurityPasswordHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "secph_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "secph_secus_id", nullable = false)
    private SecUser secphSecus;

    @Size(max = 255)
    @NotNull
    @Column(name = "secph_password_hash", nullable = false)
    private String secphPasswordHash;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "secph_changed_at")
    private OffsetDateTime secphChangedAt;

}