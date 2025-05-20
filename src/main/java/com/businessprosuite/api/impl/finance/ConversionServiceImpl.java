package com.businessprosuite.api.impl.finance;

import com.businessprosuite.api.dto.finance.ConversionDTO;
import com.businessprosuite.api.model.finance.Conversion;
import com.businessprosuite.api.model.finance.Currency;
import com.businessprosuite.api.repository.finance.ConversionRepository;
import com.businessprosuite.api.repository.finance.CurrencyRepository;
import com.businessprosuite.api.service.finance.ConversionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ConversionServiceImpl implements ConversionService {

    private final ConversionRepository conversionRepo;
    private final CurrencyRepository   currencyRepo;

    @Override
    public List<ConversionDTO> findAll() {
        return conversionRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ConversionDTO findById(Integer id) {
        Conversion conv = conversionRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Conversion not found with id " + id));
        return toDto(conv);
    }

    @Override
    public ConversionDTO create(ConversionDTO dto) {
        Currency from = currencyRepo.findById(dto.getFromCurrencyId())
                .orElseThrow(() -> new EntityNotFoundException("Currency not found with id " + dto.getFromCurrencyId()));
        Currency to = currencyRepo.findById(dto.getToCurrencyId())
                .orElseThrow(() -> new EntityNotFoundException("Currency not found with id " + dto.getToCurrencyId()));

        Conversion conv = new Conversion();
        conv.setFinConvCurFrom(from);
        conv.setFinConvCurTo(to);
        conv.setFinConvFactor(dto.getFactor());
        conv.setFinConvDescription(dto.getDescription());
        conv.setFinConvCreatedAt(LocalDateTime.now());
        conv.setFinConvUpdatedAt(LocalDateTime.now());

        Conversion saved = conversionRepo.save(conv);
        return toDto(saved);
    }

    @Override
    public ConversionDTO update(Integer id, ConversionDTO dto) {
        Conversion conv = conversionRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Conversion not found with id " + id));
        Currency from = currencyRepo.findById(dto.getFromCurrencyId())
                .orElseThrow(() -> new EntityNotFoundException("Currency not found with id " + dto.getFromCurrencyId()));
        Currency to = currencyRepo.findById(dto.getToCurrencyId())
                .orElseThrow(() -> new EntityNotFoundException("Currency not found with id " + dto.getToCurrencyId()));

        conv.setFinConvCurFrom(from);
        conv.setFinConvCurTo(to);
        conv.setFinConvFactor(dto.getFactor());
        conv.setFinConvDescription(dto.getDescription());
        conv.setFinConvUpdatedAt(LocalDateTime.now());

        Conversion updated = conversionRepo.save(conv);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!conversionRepo.existsById(id)) {
            throw new EntityNotFoundException("Conversion not found with id " + id);
        }
        conversionRepo.deleteById(id);
    }

    private ConversionDTO toDto(Conversion conv) {
        return ConversionDTO.builder()
                .id(conv.getId())
                .fromCurrencyId(conv.getFinConvCurFrom().getId())
                .toCurrencyId(conv.getFinConvCurTo().getId())
                .factor(conv.getFinConvFactor())
                .description(conv.getFinConvDescription())
                .createdAt(conv.getFinConvCreatedAt())
                .updatedAt(conv.getFinConvUpdatedAt())
                .build();
    }
}