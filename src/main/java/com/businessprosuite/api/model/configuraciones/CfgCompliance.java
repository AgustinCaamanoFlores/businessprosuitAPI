package com.businessprosuite.api.model.configuraciones;

import com.businessprosuite.api.model.company.ComCompany;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cfg_compliance", schema = "BusinessProSuite")
public class CfgCompliance {
    @EmbeddedId
    private CfgComplianceId id;

    @MapsId("cfgComplianceCmpId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "cfg_compliance_cmp_id", nullable = false)
    private ComCompany cfgComplianceCmp;

    @NotNull
    @Column(name = "cfg_compliance_mandatory", nullable = false)
    private Byte cfgComplianceMandatory;

}