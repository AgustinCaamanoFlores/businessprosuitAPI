package com.businessprosuite.api.model.user;

import com.businessprosuite.api.model.company.ComCompany;
import com.businessprosuite.api.model.configuraciones.CfgPai;
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
@Table(name = "usr_users", schema = "BusinessProSuite", indexes = {
        @Index(name = "idx_residence", columnList = "usr_residence")
}, uniqueConstraints = {
        @UniqueConstraint(name = "usr_email", columnNames = {"usr_email"})
})
public class UsrUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "usr_cmp_id", nullable = false)
    private ComCompany usrCmp;

    @Size(max = 100)
    @NotNull
    @Column(name = "usr_first_name", nullable = false, length = 100)
    private String usrFirstName;

    @Size(max = 100)
    @NotNull
    @Column(name = "usr_last_name", nullable = false, length = 100)
    private String usrLastName;

    @Size(max = 255)
    @Column(name = "usr_email")
    private String usrEmail;

    @Size(max = 50)
    @Column(name = "usr_phone", length = 50)
    private String usrPhone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usr_residence")
    private CfgPai usrResidence;

    @ColumnDefault("0")
    @Column(name = "usr_consent")
    private Byte usrConsent;

    @Column(name = "usr_consent_date")
    private LocalDateTime usrConsentDate;

    @Size(max = 255)
    @Column(name = "usr_address")
    private String usrAddress;

    @Size(max = 50)
    @Column(name = "usr_id_number", length = 50)
    private String usrIdNumber;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "usr_created_at", nullable = false)
    private LocalDateTime usrCreatedAt;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "usr_updated_at", nullable = false)
    private LocalDateTime usrUpdatedAt;

    @Builder.Default
    @OneToMany(mappedBy = "usrConsentUsr")
    private Set<UsrConsent> usrConsents = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "usrPrefUsr")
    private Set<UsrPref> usrPrefs = new LinkedHashSet<>();

    @Builder.Default
    @ManyToMany(mappedBy = "usrUsers")
    private Set<UsrRole> usrRoles = new LinkedHashSet<>();

}