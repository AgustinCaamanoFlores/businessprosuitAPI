package com.businessprosuite.api.impl.finance;

import com.businessprosuite.api.dto.finance.JournalDetailDTO;
import com.businessprosuite.api.model.finance.JournalDetail;
import com.businessprosuite.api.model.finance.Journal;
import com.businessprosuite.api.model.finance.FinanceCOA;
import com.businessprosuite.api.repository.finance.JournalDetailRepository;
import com.businessprosuite.api.repository.finance.JournalRepository;
import com.businessprosuite.api.repository.finance.FinanceCOARepository;
import com.businessprosuite.api.service.finance.JournalDetailService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class JournalDetailServiceImpl implements JournalDetailService {

    private final JournalDetailRepository detailRepo;
    private final JournalRepository journalRepo;
    private final FinanceCOARepository coaRepo;

    @Override
    public List<JournalDetailDTO> findAll() {
        return detailRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public JournalDetailDTO findById(Integer id) {
        JournalDetail detail = detailRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("JournalDetail not found with id " + id));
        return toDto(detail);
    }

    @Override
    public JournalDetailDTO create(JournalDetailDTO dto) {
        Journal journal = journalRepo.findById(dto.getJournalId())
                .orElseThrow(() -> new EntityNotFoundException("Journal not found with id " + dto.getJournalId()));
        FinanceCOA coa = coaRepo.findById(dto.getCoaId())
                .orElseThrow(() -> new EntityNotFoundException("FinanceCOA not found with id " + dto.getCoaId()));

        JournalDetail detail = new JournalDetail();
        detail.setJournalDetailJournal(journal);
        detail.setFinJournalDetailFinanceCOA(coa);
        detail.setFinJournalDetailDebit(dto.getDebit());
        detail.setFinJournalDetailCredit(dto.getCredit());
        detail.setFinJournalDetailDescription(dto.getDescription());

        JournalDetail saved = detailRepo.save(detail);
        return toDto(saved);
    }

    @Override
    public JournalDetailDTO update(Integer id, JournalDetailDTO dto) {
        JournalDetail detail = detailRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("JournalDetail not found with id " + id));
        Journal journal = journalRepo.findById(dto.getJournalId())
                .orElseThrow(() -> new EntityNotFoundException("Journal not found with id " + dto.getJournalId()));
        FinanceCOA coa = coaRepo.findById(dto.getCoaId())
                .orElseThrow(() -> new EntityNotFoundException("FinanceCOA not found with id " + dto.getCoaId()));

        detail.setJournalDetailJournal(journal);
        detail.setFinJournalDetailFinanceCOA(coa);
        detail.setFinJournalDetailDebit(dto.getDebit());
        detail.setFinJournalDetailCredit(dto.getCredit());
        detail.setFinJournalDetailDescription(dto.getDescription());

        JournalDetail updated = detailRepo.save(detail);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!detailRepo.existsById(id)) {
            throw new EntityNotFoundException("JournalDetail not found with id " + id);
        }
        detailRepo.deleteById(id);
    }

    private JournalDetailDTO toDto(JournalDetail detail) {
        return JournalDetailDTO.builder()
                .id(detail.getId())
                .journalId(detail.getJournalDetailJournal().getId())
                .coaId(detail.getFinJournalDetailFinanceCOA().getId())
                .debit(detail.getFinJournalDetailDebit())
                .credit(detail.getFinJournalDetailCredit())
                .description(detail.getFinJournalDetailDescription())
                .build();
    }
}