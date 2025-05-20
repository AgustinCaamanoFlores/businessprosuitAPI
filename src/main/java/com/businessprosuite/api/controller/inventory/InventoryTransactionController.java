package com.businessprosuite.api.controller.inventory;

import com.businessprosuite.api.dto.inventory.InventoryTransactionDTO;
import com.businessprosuite.api.service.inventory.InventoryTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory-transactions")
@RequiredArgsConstructor
public class InventoryTransactionController {

    private final InventoryTransactionService service;

    @GetMapping
    public ResponseEntity<List<InventoryTransactionDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryTransactionDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<InventoryTransactionDTO> create(@RequestBody InventoryTransactionDTO dto) {
        InventoryTransactionDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryTransactionDTO> update(
            @PathVariable Integer id,
            @RequestBody InventoryTransactionDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
