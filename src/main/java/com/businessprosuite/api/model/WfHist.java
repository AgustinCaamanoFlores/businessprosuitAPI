package com.businessprosuite.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "wf_hist", schema = "BusinessProSuite")
public class WfHist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wf_hist_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wf_ins_id", nullable = false)
    private com.businessprosuite.api.model.WfInstancia wfIns;

    @Column(name = "wf_de")
    private Integer wfDe;

    @Column(name = "wf_a")
    private Integer wfA;

    @NotNull
    @Column(name = "wf_fecha", nullable = false)
    private LocalDateTime wfFecha;

    @Column(name = "wf_usuario")
    private Integer wfUsuario;

}