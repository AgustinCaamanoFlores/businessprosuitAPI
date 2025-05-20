package com.businessprosuite.api.impl.analytics;

import com.businessprosuite.api.dto.analytics.MatViewSaleDTO;
import com.businessprosuite.api.model.analytics.MatViewSale;
import com.businessprosuite.api.model.analytics.MatViewSaleId;
import com.businessprosuite.api.repository.analytics.MatViewSaleRepository;
import com.businessprosuite.api.service.analytics.MatViewSaleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MatViewSaleServiceImpl implements MatViewSaleService {

    private final MatViewSaleRepository repo;

    @Override
    @Transactional(readOnly = true)
    public List<MatViewSaleDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public MatViewSaleDTO findById(MatViewSaleId id) {
        MatViewSale m = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("MatViewSale not found with id " + id));
        return toDto(m);
    }

    @Override
    public MatViewSaleDTO create(MatViewSaleDTO dto) {
        MatViewSale m = new MatViewSale();
        m.setId(dto.getId());
        m.setTotalSales(dto.getTotalSales());
        m.setUpdatedAt(dto.getUpdatedAt());
        MatViewSale saved = repo.save(m);
        return toDto(saved);
    }

    @Override
    public MatViewSaleDTO update(MatViewSaleId id, MatViewSaleDTO dto) {
        MatViewSale m = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("MatViewSale not found with id " + id));
        m.setTotalSales(dto.getTotalSales());
        m.setUpdatedAt(dto.getUpdatedAt());
        MatViewSale updated = repo.save(m);
        return toDto(updated);
    }

    @Override
    public void delete(MatViewSaleId id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("MatViewSale not found with id " + id);
        }
        repo.deleteById(id);
    }

    private MatViewSaleDTO toDto(MatViewSale m) {
        return MatViewSaleDTO.builder()
                .id(m.getId())
                .totalSales(m.getTotalSales())
                .updatedAt(m.getUpdatedAt())
                .build();
    }
}