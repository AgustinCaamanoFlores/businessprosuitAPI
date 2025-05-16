package com.businessprosuite.api.model.security;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sec_role_perm", schema = "BusinessProSuite", uniqueConstraints = {
        @UniqueConstraint(name = "uq_role_perm", columnNames = {"sec_rp_secrl_id", "sec_rp_secperm_id"})
})
public class SecurityRolePerm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sec_rp_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sec_rp_secrl_id", nullable = false)
    private SecurityRole secRpSecrl;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sec_rp_secperm_id", nullable = false)
    private SecurityPermission secRpSecperm;

}