package com.businessprosuite.api.model.user;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class UsrUserRoleId implements Serializable {
    private static final long serialVersionUID = 8805116832546832812L;
    @NotNull
    @Column(name = "usr_user_role_usr_id", nullable = false)
    private Integer usrUserRoleUsrId;

    @NotNull
    @Column(name = "usr_user_role_role_id", nullable = false)
    private Integer usrUserRoleRoleId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UsrUserRoleId entity = (UsrUserRoleId) o;
        return Objects.equals(this.usrUserRoleUsrId, entity.usrUserRoleUsrId) &&
                Objects.equals(this.usrUserRoleRoleId, entity.usrUserRoleRoleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usrUserRoleUsrId, usrUserRoleRoleId);
    }

}