package com.businessprosuite.api.model.configuraciones;

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
@Table(name = "cfg_module_params", schema = "BusinessProSuite")
public class CfgModuleParam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "param_id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "module_name", nullable = false, length = 50)
    private String moduleName;

    @Size(max = 100)
    @NotNull
    @Column(name = "param_key", nullable = false, length = 100)
    private String paramKey;

    @Size(max = 255)
    @Column(name = "param_value")
    private String paramValue;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ColumnDefault("1")
    @JoinColumn(name = "empresa_id", nullable = false)
    private CfgEmpresa empresa;

}