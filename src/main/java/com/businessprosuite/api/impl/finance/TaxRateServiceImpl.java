package com.businessprosuite.api.impl.finance;

import com.businessprosuite.api.dto.finance.TaxRateDTO;
import com.businessprosuite.api.model.config.ConfigCountry;
import com.businessprosuite.api.model.finance.TaxRate;
import com.businessprosuite.api.repository.config.ConfigCountryRepository;
import com.businessprosuite.api.repository.finance.TaxRateRepository;
import com.businessprosuite.api.service.finance.TaxRateService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TaxRateServiceImpl implements TaxRateService {

    private final TaxRateRepository taxRateRepo;
    private final ConfigCountryRepository countryRepo;

    @Override
    public List<TaxRateDTO> findAll() {
        return taxRateRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaxRateDTO findById(Integer id) {
        TaxRate entity = taxRateRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TaxRate not found with id " + id));
        return toDto(entity);
    }

    @Override
    public TaxRateDTO create(TaxRateDTO dto) {
        ConfigCountry country = countryRepo.findById(dto.getCountryCode())
                .orElseThrow(() -> new EntityNotFoundException("ConfigCountry not found with code " + dto.getCountryCode()));

        TaxRate rate = new TaxRate();
        rate.setCountryCode(country);
        rate.setValidFrom(dto.getValidFrom());
        rate.setRate(dto.getRate());

        TaxRate saved = taxRateRepo.save(rate);
        return toDto(saved);
    }

    @Override
    public TaxRateDTO update(Integer id, TaxRateDTO dto) {
        TaxRate rate = taxRateRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TaxRate not found with id " + id));
        ConfigCountry country = countryRepo.findById(dto.getCountryCode())
                .orElseThrow(() -> new EntityNotFoundException("ConfigCountry not found with code " + dto.getCountryCode()));

        rate.setCountryCode(country);
        rate.setValidFrom(dto.getValidFrom());
        rate.setRate(dto.getRate());

        TaxRate updated = taxRateRepo.save(rate);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!taxRateRepo.existsById(id)) {
            throw new EntityNotFoundException("TaxRate not found with id " + id);
        }
        taxRateRepo.deleteById(id);
    }

    private TaxRateDTO toDto(TaxRate rate) {
        return TaxRateDTO.builder()
                .id(rate.getId())
                .countryCode(rate.getCountryCode().getCodigo())
                .validFrom(rate.getValidFrom())
                .rate(rate.getRate())
                .build();
    }
}
