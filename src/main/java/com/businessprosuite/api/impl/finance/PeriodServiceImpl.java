package com.businessprosuite.api.impl.finance;

import com.businessprosuite.api.dto.finance.PeriodDTO;
import com.businessprosuite.api.model.finance.Period;
import com.businessprosuite.api.model.company.Company;
import com.businessprosuite.api.repository.finance.PeriodRepository;
import com.businessprosuite.api.repository.company.CompanyRepository;
import com.businessprosuite.api.service.finance.PeriodService;
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
public class PeriodServiceImpl implements PeriodService {

    private final PeriodRepository periodRepo;
    private final CompanyRepository companyRepo;

    @Override
    public List<PeriodDTO> findAll() {
        return periodRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PeriodDTO findById(Integer id) {
        Period period = periodRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Period not found with id " + id));
        return toDto(period);
    }

    @Override
    public PeriodDTO create(PeriodDTO dto) {
        Company company = companyRepo.findById(dto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("Company not found with id " + dto.getCompanyId()));

        Period period = new Period();
        period.setFinPeriodName(dto.getName());
        period.setFinPeriodStart(dto.getStartDate());
        period.setFinPeriodEnd(dto.getEndDate());
        period.setFinPeriodIsClosed(dto.getIsClosed());
        period.setFinPeriodCmp(company);
        period.setFinPeriodCreatedAt(LocalDateTime.now());

        Period saved = periodRepo.save(period);
        return toDto(saved);
    }

    @Override
    public PeriodDTO update(Integer id, PeriodDTO dto) {
        Period period = periodRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Period not found with id " + id));
        Company company = companyRepo.findById(dto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("Company not found with id " + dto.getCompanyId()));

        period.setFinPeriodName(dto.getName());
        period.setFinPeriodStart(dto.getStartDate());
        period.setFinPeriodEnd(dto.getEndDate());
        period.setFinPeriodIsClosed(dto.getIsClosed());
        period.setFinPeriodCmp(company);
        // createdAt remains unchanged

        Period updated = periodRepo.save(period);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!periodRepo.existsById(id)) {
            throw new EntityNotFoundException("Period not found with id " + id);
        }
        periodRepo.deleteById(id);
    }

    private PeriodDTO toDto(Period period) {
        return PeriodDTO.builder()
                .id(period.getId())
                .name(period.getFinPeriodName())
                .startDate(period.getFinPeriodStart())
                .endDate(period.getFinPeriodEnd())
                .isClosed(period.getFinPeriodIsClosed())
                .companyId(period.getFinPeriodCmp().getId())
                .createdAt(period.getFinPeriodCreatedAt())
                .build();
    }
}