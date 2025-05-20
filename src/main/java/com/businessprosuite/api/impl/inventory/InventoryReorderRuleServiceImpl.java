package com.businessprosuite.api.service.inventory.impl;

import com.businessprosuite.api.dto.inventory.InventoryReorderRuleDTO;
import com.businessprosuite.api.model.inventory.InventoryReorderRule;
import com.businessprosuite.api.model.inventory.InventoryProduct;
import com.businessprosuite.api.repository.inventory.InventoryReorderRuleRepository;
import com.businessprosuite.api.repository.inventory.InventoryProductRepository;
import com.businessprosuite.api.service.inventory.InventoryReorderRuleService;
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
public class InventoryReorderRuleServiceImpl implements InventoryReorderRuleService {

    private final InventoryReorderRuleRepository ruleRepo;
    private final InventoryProductRepository productRepo;

    @Override
    public List<InventoryReorderRuleDTO> findAll() {
        return ruleRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public InventoryReorderRuleDTO findById(Integer id) {
        InventoryReorderRule rule = ruleRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("InventoryReorderRule not found with id " + id));
        return toDto(rule);
    }

    @Override
    public InventoryReorderRuleDTO create(InventoryReorderRuleDTO dto) {
        InventoryProduct prod = productRepo.findById(dto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("InventoryProduct not found with id " + dto.getProductId()));

        InventoryReorderRule rule = new InventoryReorderRule();
        rule.setProd(prod);
        rule.setThreshold(dto.getThreshold());
        rule.setOrderQty(dto.getOrderQuantity());
        rule.setLastRun(dto.getLastRun());

        InventoryReorderRule saved = ruleRepo.save(rule);
        return toDto(saved);
    }

    @Override
    public InventoryReorderRuleDTO update(Integer id, InventoryReorderRuleDTO dto) {
        InventoryReorderRule rule = ruleRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("InventoryReorderRule not found with id " + id));
        InventoryProduct prod = productRepo.findById(dto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("InventoryProduct not found with id " + dto.getProductId()));

        rule.setProd(prod);
        rule.setThreshold(dto.getThreshold());
        rule.setOrderQty(dto.getOrderQuantity());
        rule.setLastRun(dto.getLastRun());

        InventoryReorderRule updated = ruleRepo.save(rule);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!ruleRepo.existsById(id)) {
            throw new EntityNotFoundException("InventoryReorderRule not found with id " + id);
        }
        ruleRepo.deleteById(id);
    }

    private InventoryReorderRuleDTO toDto(InventoryReorderRule rule) {
        return InventoryReorderRuleDTO.builder()
                .id(rule.getId())
                .productId(rule.getProd().getId())
                .threshold(rule.getThreshold())
                .orderQuantity(rule.getOrderQty())
                .lastRun(rule.getLastRun())
                .build();
    }
}