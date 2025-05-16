package com.businessprosuite.api.model.user;

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
@Table(name = "usr_prefs", schema = "BusinessProSuite")
public class UsrPref {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_pref_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "usr_pref_usr_id", nullable = false)
    private UsrUser usrPrefUsr;

    @Size(max = 100)
    @NotNull
    @Column(name = "usr_pref_key", nullable = false, length = 100)
    private String usrPrefKey;

    @Size(max = 255)
    @NotNull
    @Column(name = "usr_pref_value", nullable = false)
    private String usrPrefValue;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "usr_pref_created_at", nullable = false)
    private LocalDateTime usrPrefCreatedAt;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "usr_pref_updated_at", nullable = false)
    private LocalDateTime usrPrefUpdatedAt;

}