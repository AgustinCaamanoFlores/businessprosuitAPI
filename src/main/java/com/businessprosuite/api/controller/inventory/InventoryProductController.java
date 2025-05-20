package com.businessprosuite.api.controller.inventory;

import com.businessprosuite.api.dto.inventory.InventoryProductDTO;
import com.businessprosuite.api.service.inventory.InventoryProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory-products")
@RequiredArgsConstructor
public class InventoryProductController {

    private final InventoryProductService service;

    @GetMapping
    public ResponseEntity<List<InventoryProductDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryProductDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<InventoryProductDTO> create(@RequestBody InventoryProductDTO dto) {
        InventoryProductDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryProductDTO> update(
            @PathVariable Integer id,
            @RequestBody InventoryProductDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
