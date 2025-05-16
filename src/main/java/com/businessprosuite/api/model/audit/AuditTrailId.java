package com.businessprosuite.api.model.audit;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class AuditTrailId implements Serializable {
    //private static final long serialVersionUID = 6642374225962892264L;
    @NotNull
    @Column(name = "auda_id", nullable = false)
    private Integer audaId;

    @NotNull
    @ColumnDefault("1")
    @Column(name = "auda_empresa_id", nullable = false)
    private Integer audaEmpresaId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AuditTrailId entity = (AuditTrailId) o;
        return Objects.equals(this.audaId, entity.audaId) &&
                Objects.equals(this.audaEmpresaId, entity.audaEmpresaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(audaId, audaEmpresaId);
    }

}