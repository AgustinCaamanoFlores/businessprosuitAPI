package com.businessprosuite.api.model.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "usr_roles", schema = "BusinessProSuite")
public class UsrRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_role_id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "usr_role_name", nullable = false, length = 50)
    private String usrRoleName;

    @Builder.Default
    @ManyToMany
    @JoinTable(name = "usr_user_roles",
            joinColumns = @JoinColumn(name = "usr_user_role_role_id"),
            inverseJoinColumns = @JoinColumn(name = "usr_user_role_usr_id"))
    private Set<UsrUser> usrUsers = new LinkedHashSet<>();

}