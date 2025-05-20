package com.businessprosuite.api.impl.analytics;

import com.businessprosuite.api.dto.analytics.DwTimeDTO;
import com.businessprosuite.api.model.analytics.DwTime;
import com.businessprosuite.api.repository.analytics.DwTimeRepository;
import com.businessprosuite.api.service.analytics.DwTimeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DwTimeServiceImpl implements DwTimeService {

    private final DwTimeRepository repo;

    @Override
    @Transactional(readOnly = true)
    public List<DwTimeDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DwTimeDTO findByDate(LocalDate date) {
        DwTime dw = repo.findById(date)
                .orElseThrow(() -> new EntityNotFoundException("DwTime not found for date " + date));
        return toDto(dw);
    }

    @Override
    public DwTimeDTO create(DwTimeDTO dto) {
        DwTime dw = new DwTime();
        dw.setId(dto.getDate());
        dw.setDtDay(dto.getDay());
        dw.setDtMonth(dto.getMonth());
        dw.setDtYear(dto.getYear());
        DwTime saved = repo.save(dw);
        return toDto(saved);
    }

    @Override
    public DwTimeDTO update(LocalDate date, DwTimeDTO dto) {
        DwTime dw = repo.findById(date)
                .orElseThrow(() -> new EntityNotFoundException("DwTime not found for date " + date));
        dw.setDtDay(dto.getDay());
        dw.setDtMonth(dto.getMonth());
        dw.setDtYear(dto.getYear());
        DwTime updated = repo.save(dw);
        return toDto(updated);
    }

    @Override
    public void delete(LocalDate date) {
        if (!repo.existsById(date)) {
            throw new EntityNotFoundException("DwTime not found for date " + date);
        }
        repo.deleteById(date);
    }

    private DwTimeDTO toDto(DwTime dw) {
        return DwTimeDTO.builder()
                .date(dw.getId())
                .day(dw.getDtDay())
                .month(dw.getDtMonth())
                .year(dw.getDtYear())
                .build();
    }
}
