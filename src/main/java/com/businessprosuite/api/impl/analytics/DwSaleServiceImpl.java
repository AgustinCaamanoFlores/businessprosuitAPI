package com.businessprosuite.api.impl.analytics;

import com.businessprosuite.api.dto.analytics.DwSaleDTO;
import com.businessprosuite.api.model.analytics.DwSale;
import com.businessprosuite.api.model.analytics.DwSaleId;
import com.businessprosuite.api.repository.analytics.DwSaleRepository;
import com.businessprosuite.api.service.analytics.DwSaleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DwSaleServiceImpl implements DwSaleService {

    private final DwSaleRepository repo;

    @Override
    @Transactional(readOnly = true)
    public List<DwSaleDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DwSaleDTO findById(DwSaleId id) {
        DwSale sale = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DwSale not found with id " + id));
        return toDto(sale);
    }

    @Override
    public DwSaleDTO create(DwSaleDTO dto) {
        DwSale sale = new DwSale();
        sale.setId(dto.getId());
        sale.setDsQuantity(dto.getQuantity());
        sale.setDsTotal(dto.getTotal());
        DwSale saved = repo.save(sale);
        return toDto(saved);
    }

    @Override
    public DwSaleDTO update(DwSaleId id, DwSaleDTO dto) {
        DwSale sale = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DwSale not found with id " + id));
        sale.setDsQuantity(dto.getQuantity());
        sale.setDsTotal(dto.getTotal());
        DwSale updated = repo.save(sale);
        return toDto(updated);
    }

    @Override
    public void delete(DwSaleId id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("DwSale not found with id " + id);
        }
        repo.deleteById(id);
    }

    private DwSaleDTO toDto(DwSale sale) {
        return DwSaleDTO.builder()
                .id(sale.getId())
                .quantity(sale.getDsQuantity())
                .total(sale.getDsTotal())
                .build();
    }
}