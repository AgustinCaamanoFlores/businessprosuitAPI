package com.businessprosuite.api.impl.inventory;

import com.businessprosuite.api.dto.inventory.InventoryTransactionDTO;
import com.businessprosuite.api.model.inventory.InventoryTransaction;
import com.businessprosuite.api.model.inventory.InventoryProduct;
import com.businessprosuite.api.repository.inventory.InventoryTransactionRepository;
import com.businessprosuite.api.repository.inventory.InventoryProductRepository;
import com.businessprosuite.api.service.inventory.InventoryTransactionService;
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
public class InventoryTransactionServiceImpl implements InventoryTransactionService {

    private final InventoryTransactionRepository transRepo;
    private final InventoryProductRepository productRepo;

    @Override
    public List<InventoryTransactionDTO> findAll() {
        return transRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public InventoryTransactionDTO findById(Integer id) {
        InventoryTransaction tx = transRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("InventoryTransaction not found with id " + id));
        return toDto(tx);
    }

    @Override
    public InventoryTransactionDTO create(InventoryTransactionDTO dto) {
        InventoryProduct prod = productRepo.findById(dto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("InventoryProduct not found with id " + dto.getProductId()));

        InventoryTransaction tx = new InventoryTransaction();
        tx.setInvItProd(prod);
        tx.setInvItDate(dto.getDate());
        tx.setItType(dto.getType());
        tx.setInvItQuantity(dto.getQuantity());
        tx.setInvItDescription(dto.getDescription());
        tx.setInvItCreatedAt(LocalDateTime.now());

        InventoryTransaction saved = transRepo.save(tx);
        return toDto(saved);
    }

    @Override
    public InventoryTransactionDTO update(Integer id, InventoryTransactionDTO dto) {
        InventoryTransaction tx = transRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("InventoryTransaction not found with id " + id));
        InventoryProduct prod = productRepo.findById(dto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("InventoryProduct not found with id " + dto.getProductId()));

        tx.setInvItProd(prod);
        tx.setInvItDate(dto.getDate());
        tx.setItType(dto.getType());
        tx.setInvItQuantity(dto.getQuantity());
        tx.setInvItDescription(dto.getDescription());
        // createdAt remains unchanged

        InventoryTransaction updated = transRepo.save(tx);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!transRepo.existsById(id)) {
            throw new EntityNotFoundException("InventoryTransaction not found with id " + id);
        }
        transRepo.deleteById(id);
    }

    private InventoryTransactionDTO toDto(InventoryTransaction tx) {
        return InventoryTransactionDTO.builder()
                .id(tx.getId())
                .productId(tx.getInvItProd().getId())
                .date(tx.getInvItDate())
                .type(tx.getItType())
                .quantity(tx.getInvItQuantity())
                .description(tx.getInvItDescription())
                .createdAt(tx.getInvItCreatedAt())
                .build();
    }
}
