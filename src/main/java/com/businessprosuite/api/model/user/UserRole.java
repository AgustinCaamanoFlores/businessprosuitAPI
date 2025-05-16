package com.businessprosuite.api.model.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "usr_user_roles", schema = "BusinessProSuite")
public class UserRole {
    @EmbeddedId
    private UserRoleId id;

    @MapsId("usrUserRoleUsrId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "usr_user_role_usr_id", nullable = false)
    private User usrUserRole;

    @MapsId("usrUserRoleRoleId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "usr_user_role_role_id", nullable = false)
    private Roles usrRoles;

}