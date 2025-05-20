package com.businessprosuite.api.controller.inventory;

import com.businessprosuite.api.dto.inventory.InventoryLotLocationHistoryDTO;
import com.businessprosuite.api.service.inventory.InventoryLotLocationHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory-lot-history")
@RequiredArgsConstructor
public class InventoryLotLocationHistoryController {

    private final InventoryLotLocationHistoryService service;

    @GetMapping
    public ResponseEntity<List<InventoryLotLocationHistoryDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryLotLocationHistoryDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<InventoryLotLocationHistoryDTO> create(@RequestBody InventoryLotLocationHistoryDTO dto) {
        InventoryLotLocationHistoryDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryLotLocationHistoryDTO> update(
            @PathVariable Integer id,
            @RequestBody InventoryLotLocationHistoryDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
