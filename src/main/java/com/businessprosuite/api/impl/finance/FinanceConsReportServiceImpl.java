package com.businessprosuite.api.impl.finance;

import com.businessprosuite.api.dto.finance.FinanceConsReportDTO;
import com.businessprosuite.api.model.company.Company;
import com.businessprosuite.api.model.finance.FinanceConsReport;
import com.businessprosuite.api.repository.company.CompanyRepository;
import com.businessprosuite.api.repository.finance.FinanceConsReportRepository;
import com.businessprosuite.api.service.finance.FinanceConsReportService;
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
public class FinanceConsReportServiceImpl implements FinanceConsReportService {

    private final FinanceConsReportRepository reportRepo;
    private final CompanyRepository             companyRepo;

    @Override
    public List<FinanceConsReportDTO> findAll() {
        return reportRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public FinanceConsReportDTO findById(Integer id) {
        FinanceConsReport report = reportRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("FinanceConsReport not found with id " + id));
        return toDto(report);
    }

    @Override
    public FinanceConsReportDTO create(FinanceConsReportDTO dto) {
        Company company = companyRepo.findById(dto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("Company not found with id " + dto.getCompanyId()));

        FinanceConsReport report = new FinanceConsReport();
        report.setFinConsRepCmp(company);
        report.setFinConsRepDate(dto.getReportDate());
        report.setFinConsRepTotalAssets(dto.getTotalAssets());
        report.setFinConsRepTotalLiabilities(dto.getTotalLiabilities());
        report.setFinConsRepNetIncome(dto.getNetIncome());
        report.setFinConsRepCurrency(dto.getCurrency());
        report.setFinConsRepConversionFactor(dto.getConversionFactor());
        report.setFinConsRepCreatedAt(LocalDateTime.now());

        FinanceConsReport saved = reportRepo.save(report);
        return toDto(saved);
    }

    @Override
    public FinanceConsReportDTO update(Integer id, FinanceConsReportDTO dto) {
        FinanceConsReport report = reportRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("FinanceConsReport not found with id " + id));
        Company company = companyRepo.findById(dto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("Company not found with id " + dto.getCompanyId()));

        report.setFinConsRepCmp(company);
        report.setFinConsRepDate(dto.getReportDate());
        report.setFinConsRepTotalAssets(dto.getTotalAssets());
        report.setFinConsRepTotalLiabilities(dto.getTotalLiabilities());
        report.setFinConsRepNetIncome(dto.getNetIncome());
        report.setFinConsRepCurrency(dto.getCurrency());
        report.setFinConsRepConversionFactor(dto.getConversionFactor());
        // createdAt remains unchanged

        FinanceConsReport updated = reportRepo.save(report);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!reportRepo.existsById(id)) {
            throw new EntityNotFoundException("FinanceConsReport not found with id " + id);
        }
        reportRepo.deleteById(id);
    }

    private FinanceConsReportDTO toDto(FinanceConsReport report) {
        return FinanceConsReportDTO.builder()
                .id(report.getId())
                .companyId(report.getFinConsRepCmp().getId())
                .reportDate(report.getFinConsRepDate())
                .totalAssets(report.getFinConsRepTotalAssets())
                .totalLiabilities(report.getFinConsRepTotalLiabilities())
                .netIncome(report.getFinConsRepNetIncome())
                .currency(report.getFinConsRepCurrency())
                .conversionFactor(report.getFinConsRepConversionFactor())
                .createdAt(report.getFinConsRepCreatedAt())
                .build();
    }
}