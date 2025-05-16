package com.businessprosuite.api.model.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "usr_consent", schema = "BusinessProSuite")
public class UserConsent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_consent_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "usr_consent_usr_id", nullable = false)
    private User usrConsentUsr;

    @Size(max = 50)
    @NotNull
    @Column(name = "usr_consent_type", nullable = false, length = 50)
    private String usrConsentType;

    @NotNull
    @Column(name = "usr_consent_granted_at", nullable = false)
    private LocalDateTime usrConsentGrantedAt;

    @Column(name = "usr_consent_revoked_at")
    private LocalDateTime usrConsentRevokedAt;

}