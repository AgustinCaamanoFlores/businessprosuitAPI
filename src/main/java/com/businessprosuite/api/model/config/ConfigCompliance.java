package com.businessprosuite.api.model.config;

import com.businessprosuite.api.model.company.Company;
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
public class ConfigCompliance {
    @EmbeddedId
    private ConfigComplianceId id;

    @MapsId("cfgComplianceCmpId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "cfg_compliance_cmp_id", nullable = false)
    private Company cfgComplianceCmp;

    @NotNull
    @Column(name = "cfg_compliance_mandatory", nullable = false)
    private Byte cfgComplianceMandatory;

}