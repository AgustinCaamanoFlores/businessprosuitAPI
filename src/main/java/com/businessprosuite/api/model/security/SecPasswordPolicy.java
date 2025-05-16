package com.businessprosuite.api.model.security;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sec_password_policy", schema = "BusinessProSuite")
public class SecPasswordPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "policy_id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @ColumnDefault("8")
    @Column(name = "min_length", nullable = false)
    private Integer minLength;

    @Builder.Default
    @NotNull
    @ColumnDefault("1")
    @Column(name = "require_upper", nullable = false)
    private Boolean requireUpper = false;

    @Builder.Default
    @NotNull
    @ColumnDefault("1")
    @Column(name = "require_digit", nullable = false)
    private Boolean requireDigit = false;

    @NotNull
    @ColumnDefault("90")
    @Column(name = "expire_days", nullable = false)
    private Integer expireDays;

    @NotNull
    @ColumnDefault("5")
    @Column(name = "reuse_forbid", nullable = false)
    private Integer reuseForbid;

}