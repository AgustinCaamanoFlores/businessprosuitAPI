package com.businessprosuite.api.controller.inventory;

import com.businessprosuite.api.dto.inventory.InventoryLotDTO;
import com.businessprosuite.api.service.inventory.InventoryLotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory-lots")
@RequiredArgsConstructor
public class InventoryLotController {

    private final InventoryLotService service;

    @GetMapping
    public ResponseEntity<List<InventoryLotDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryLotDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<InventoryLotDTO> create(@RequestBody InventoryLotDTO dto) {
        InventoryLotDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryLotDTO> update(
            @PathVariable Integer id,
            @RequestBody InventoryLotDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
