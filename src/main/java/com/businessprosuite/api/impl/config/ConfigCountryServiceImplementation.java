package com.businessprosuite.api.impl.config;

import com.businessprosuite.api.dto.config.ConfigCountryDTO;
import com.businessprosuite.api.model.config.ConfigCountry;
import com.businessprosuite.api.repository.config.ConfigCountryRepository;
import com.businessprosuite.api.service.config.ConfigCountryService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ConfigCountryServiceImplementation implements ConfigCountryService {

    private final ConfigCountryRepository repo;

    public ConfigCountryServiceImplementation(ConfigCountryRepository repo) {
        this.repo = repo;
    }

    @Override
    @Cacheable("configCountries")
    public List<ConfigCountryDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ConfigCountryDTO findByCode(String code) {
        ConfigCountry entidad = repo.findById(code)
                .orElseThrow(() -> new IllegalArgumentException(
                        "ConfigCountry no encontrado: " + code));
        return toDto(entidad);
    }

    @Override
    public ConfigCountryDTO create(ConfigCountryDTO dto) {
        ConfigCountry entidad = toEntity(dto);
        ConfigCountry guardado = repo.save(entidad);
        return toDto(guardado);
    }

    @Override
    public ConfigCountryDTO update(String code, ConfigCountryDTO dto) {
        ConfigCountry existente = repo.findById(code)
                .orElseThrow(() -> new IllegalArgumentException(
                        "ConfigCountry no encontrado: " + code));
        // Actualiza sólo los campos editables
        existente.setCfgPaisNombre(dto.getName());
        existente.setAccountingStandard(dto.getAccountingStandard());
        existente.setPrivacyLaw(dto.getPrivacyLaw());
        existente.setDataLocalization(dto.getDataLocalization());
        existente.setPredominantSector(dto.getPredominantSector());
        existente.setMonedaPorDefecto(dto.getDefaultCurrency());
        existente.setSimboloMoneda(dto.getCurrencySymbol());
        existente.setFormatoFecha(dto.getDateFormat());
        existente.setDecimalesImpuestos(dto.getTaxDecimals());
        ConfigCountry actualizado = repo.save(existente);
        return toDto(actualizado);
    }

    @Override
    public void delete(String code) {
        repo.deleteById(code);
    }

    // --- mapeo interno Entidad ↔ DTO ---
    private ConfigCountryDTO toDto(ConfigCountry e) {
        return ConfigCountryDTO.builder()
                .code(e.getCodigo())
                .name(e.getCfgPaisNombre())
                .accountingStandard(e.getAccountingStandard())
                .privacyLaw(e.getPrivacyLaw())
                .dataLocalization(e.getDataLocalization())
                .predominantSector(e.getPredominantSector())
                .defaultCurrency(e.getMonedaPorDefecto())
                .currencySymbol(e.getSimboloMoneda())
                .dateFormat(e.getFormatoFecha())
                .taxDecimals(e.getDecimalesImpuestos())
                .build();
    }

    private ConfigCountry toEntity(ConfigCountryDTO d) {
        ConfigCountry e = new ConfigCountry();
        e.setCodigo(d.getCode());
        e.setCfgPaisNombre(d.getName());
        e.setAccountingStandard(d.getAccountingStandard());
        e.setPrivacyLaw(d.getPrivacyLaw());
        e.setDataLocalization(d.getDataLocalization());
        e.setPredominantSector(d.getPredominantSector());
        e.setMonedaPorDefecto(d.getDefaultCurrency());
        e.setSimboloMoneda(d.getCurrencySymbol());
        e.setFormatoFecha(d.getDateFormat());
        e.setDecimalesImpuestos(d.getTaxDecimals());
        return e;
    }
}
