package com.businessprosuite.api.model.security;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sec_permissions", schema = "BusinessProSuite", uniqueConstraints = {
        @UniqueConstraint(name = "secperm_name_UNIQUE", columnNames = {"secperm_name"})
})
public class SecPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "secperm_id", nullable = false)
    private Integer id;

    @Size(max = 45)
    @NotNull
    @Column(name = "secperm_name", nullable = false, length = 45)
    private String secpermName;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "secperm_created_at", nullable = false)
    private LocalDateTime secpermCreatedAt;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "secperm_updated_at", nullable = false)
    private LocalDateTime secpermUpdatedAt;

    @Builder.Default
    @OneToMany(mappedBy = "secRpSecperm")
    private Set<SecRolePerm> secRolePerms = new LinkedHashSet<>();

}