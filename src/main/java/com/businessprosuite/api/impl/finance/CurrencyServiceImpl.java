package com.businessprosuite.api.impl.finance;

import com.businessprosuite.api.dto.finance.CurrencyDTO;
import com.businessprosuite.api.model.finance.Currency;
import com.businessprosuite.api.repository.finance.CurrencyRepository;
import com.businessprosuite.api.service.finance.CurrencyService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository repo;

    @Override
    @Cacheable("currencies")
    public List<CurrencyDTO> findAll() {
        return repo.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CurrencyDTO findById(Integer id) {
        Currency entity = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Currency not found with id " + id));
        return toDto(entity);
    }

    @Override
    public CurrencyDTO create(CurrencyDTO dto) {
        Currency currency = new Currency();
        currency.setFinCurCode(dto.getCode());
        currency.setFinCurName(dto.getName());
        currency.setFinCurExchangeRate(dto.getExchangeRate());
        currency.setFinCurLastUpdated(dto.getLastUpdated() != null ? dto.getLastUpdated() : LocalDateTime.now());
        currency.setFinCurCreatedAt(LocalDateTime.now());
        currency.setFinCurUpdatedAt(LocalDateTime.now());
        Currency saved = repo.save(currency);
        return toDto(saved);
    }

    @Override
    public CurrencyDTO update(Integer id, CurrencyDTO dto) {
        Currency currency = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Currency not found with id " + id));
        currency.setFinCurCode(dto.getCode());
        currency.setFinCurName(dto.getName());
        currency.setFinCurExchangeRate(dto.getExchangeRate());
        currency.setFinCurLastUpdated(dto.getLastUpdated() != null ? dto.getLastUpdated() : LocalDateTime.now());
        currency.setFinCurUpdatedAt(LocalDateTime.now());
        Currency updated = repo.save(currency);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("Currency not found with id " + id);
        }
        repo.deleteById(id);
    }

    private CurrencyDTO toDto(Currency currency) {
        return CurrencyDTO.builder()
                .id(currency.getId())
                .code(currency.getFinCurCode())
                .name(currency.getFinCurName())
                .exchangeRate(currency.getFinCurExchangeRate())
                .lastUpdated(currency.getFinCurLastUpdated())
                .createdAt(currency.getFinCurCreatedAt())
                .updatedAt(currency.getFinCurUpdatedAt())
                .build();
    }
}