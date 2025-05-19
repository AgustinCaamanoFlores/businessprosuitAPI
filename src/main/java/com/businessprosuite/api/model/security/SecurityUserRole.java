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
@Table(name = "sec_user_role", schema = "BusinessProSuite", uniqueConstraints = {
        @UniqueConstraint(name = "uq_user_role", columnNames = {"sec_ur_secus_id", "sec_ur_secrl_id"})
})
public class SecurityUserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sec_ur_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sec_ur_secus_id", nullable = false)
    private SecurityUser secUrSecus;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sec_ur_secrl_id", nullable = false)
    private SecurityRole secUrSecrl;

}