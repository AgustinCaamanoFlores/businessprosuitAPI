package com.businessprosuite.api.model.config;

import com.businessprosuite.api.model.customer.Customer;
import com.businessprosuite.api.model.company.Company;
import com.businessprosuite.api.model.finance.TaxRate;
import com.businessprosuite.api.model.user.User;
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
@Table(name = "cfg_pais", schema = "BusinessProSuite")
public class ConfigCountry {
    @Id
    @Size(max = 2)
    @Column(name = "codigo", nullable = false, length = 2)
    private String codigo;

    @Size(max = 100)
    @NotNull
    @Column(name = "cfg_pais_nombre", nullable = false, length = 100)
    private String cfgPaisNombre;

    @Size(max = 50)
    @Column(name = "accounting_standard", length = 50)
    private String accountingStandard;

    @Size(max = 50)
    @Column(name = "privacy_law", length = 50)
    private String privacyLaw;

    @Column(name = "data_localization")
    private Byte dataLocalization;

    @Size(max = 50)
    @Column(name = "predominant_sector", length = 50)
    private String predominantSector;

    @Size(max = 3)
    @Column(name = "moneda_por_defecto", length = 3)
    private String monedaPorDefecto;

    @Size(max = 10)
    @Column(name = "simbolo_moneda", length = 10)
    private String simboloMoneda;

    @Size(max = 20)
    @Column(name = "formato_fecha", length = 20)
    private String formatoFecha;

    @ColumnDefault("2")
    @Column(name = "decimales_impuestos")
    private Integer decimalesImpuestos;

    @Builder.Default
    @OneToMany(mappedBy = "cmpConfigCountryCodigo")
    private Set<Company> comCompanies = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "cusConfigCountryCodigo")
    private Set<Customer> customers = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "countryCode")
    private Set<TaxRate> taxRates = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "usrResidence")
    private Set<User> users = new LinkedHashSet<>();

}