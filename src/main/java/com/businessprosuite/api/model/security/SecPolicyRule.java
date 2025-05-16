package com.businessprosuite.api.model.security;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sec_policy_rules", schema = "BusinessProSuite")
public class SecPolicyRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rule_id", nullable = false)
    private Integer id;

    @NotNull
    @Lob
    @Column(name = "expr", nullable = false)
    private String expr;

    @Size(max = 255)
    @Column(name = "description")
    private String description;

}