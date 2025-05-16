package com.businessprosuite.api.model.config;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class ConfigComplianceId implements Serializable {
    //private static final long serialVersionUID = 7789669003440281430L;
    @NotNull
    @Column(name = "cfg_compliance_cmp_id", nullable = false)
    private Integer cfgComplianceCmpId;

    @Size(max = 50)
    @NotNull
    @Column(name = "cfg_compliance_standard", nullable = false, length = 50)
    private String cfgComplianceStandard;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ConfigComplianceId entity = (ConfigComplianceId) o;
        return Objects.equals(this.cfgComplianceCmpId, entity.cfgComplianceCmpId) &&
                Objects.equals(this.cfgComplianceStandard, entity.cfgComplianceStandard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cfgComplianceCmpId, cfgComplianceStandard);
    }

}