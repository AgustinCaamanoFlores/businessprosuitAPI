package com.businessprosuite.api.model.activos;

import com.businessprosuite.api.model.configuraciones.CfgEmpresa;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "act_categoria", schema = "BusinessProSuite")
public class ActCategoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "act_cat_id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "act_cat_nombre", nullable = false, length = 50)
    private String actCatNombre;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ColumnDefault("1")
    @JoinColumn(name = "empresa_id", nullable = false)
    private CfgEmpresa empresa;

    @Builder.Default
    @OneToMany(mappedBy = "actCat")
    private Set<ActActivo> actActivos = new LinkedHashSet<>();

}