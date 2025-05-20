package com.businessprosuite.api.impl.finance;

import com.businessprosuite.api.dto.finance.FiscalRuleDTO;
import com.businessprosuite.api.model.finance.FiscalRule;
import com.businessprosuite.api.repository.finance.FiscalRuleRepository;
import com.businessprosuite.api.service.finance.FiscalRuleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FiscalRuleServiceImpl implements FiscalRuleService {

    private final FiscalRuleRepository repo;

    @Override
    public List<FiscalRuleDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public FiscalRuleDTO findById(Integer id) {
        FiscalRule rule = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("FiscalRule not found with id " + id));
        return toDto(rule);
    }

    @Override
    public FiscalRuleDTO create(FiscalRuleDTO dto) {
        FiscalRule rule = new FiscalRule();
        rule.setFromCode(dto.getFromCode());
        rule.setToCode(dto.getToCode());
        rule.setSpreadPct(dto.getSpreadPct());
        rule.setRounding(dto.getRounding());
        FiscalRule saved = repo.save(rule);
        return toDto(saved);
    }

    @Override
    public FiscalRuleDTO update(Integer id, FiscalRuleDTO dto) {
        FiscalRule rule = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("FiscalRule not found with id " + id));
        rule.setFromCode(dto.getFromCode());
        rule.setToCode(dto.getToCode());
        rule.setSpreadPct(dto.getSpreadPct());
        rule.setRounding(dto.getRounding());
        FiscalRule updated = repo.save(rule);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("FiscalRule not found with id " + id);
        }
        repo.deleteById(id);
    }

    private FiscalRuleDTO toDto(FiscalRule rule) {
        return FiscalRuleDTO.builder()
                .id(rule.getId())
                .fromCode(rule.getFromCode())
                .toCode(rule.getToCode())
                .spreadPct(rule.getSpreadPct())
                .rounding(rule.getRounding())
                .build();
    }
}