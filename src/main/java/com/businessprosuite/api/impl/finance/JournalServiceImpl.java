package com.businessprosuite.api.impl.finance;

import com.businessprosuite.api.dto.finance.JournalDTO;
import com.businessprosuite.api.model.finance.Journal;
import com.businessprosuite.api.model.company.Company;
import com.businessprosuite.api.repository.finance.JournalRepository;
import com.businessprosuite.api.repository.company.CompanyRepository;
import com.businessprosuite.api.service.finance.JournalService;
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
public class JournalServiceImpl implements JournalService {

    private final JournalRepository journalRepo;
    private final CompanyRepository companyRepo;

    @Override
    public List<JournalDTO> findAll() {
        return journalRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public JournalDTO findById(Integer id) {
        Journal journal = journalRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Journal not found with id " + id));
        return toDto(journal);
    }

    @Override
    public JournalDTO create(JournalDTO dto) {
        Company company = companyRepo.findById(dto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("Company not found with id " + dto.getCompanyId()));

        Journal journal = new Journal();
        journal.setFinJournalDate(dto.getDate());
        journal.setFinJournalDescription(dto.getDescription());
        journal.setFinJournalCmp(company);
        journal.setFinJournalCreatedAt(LocalDateTime.now());

        Journal saved = journalRepo.save(journal);
        return toDto(saved);
    }

    @Override
    public JournalDTO update(Integer id, JournalDTO dto) {
        Journal journal = journalRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Journal not found with id " + id));
        Company company = companyRepo.findById(dto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("Company not found with id " + dto.getCompanyId()));

        journal.setFinJournalDate(dto.getDate());
        journal.setFinJournalDescription(dto.getDescription());
        journal.setFinJournalCmp(company);
        // createdAt remains unchanged

        Journal updated = journalRepo.save(journal);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!journalRepo.existsById(id)) {
            throw new EntityNotFoundException("Journal not found with id " + id);
        }
        journalRepo.deleteById(id);
    }

    private JournalDTO toDto(Journal journal) {
        return JournalDTO.builder()
                .id(journal.getId())
                .date(journal.getFinJournalDate())
                .description(journal.getFinJournalDescription())
                .companyId(journal.getFinJournalCmp().getId())
                .createdAt(journal.getFinJournalCreatedAt())
                .build();
    }
}