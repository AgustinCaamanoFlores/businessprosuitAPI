package com.businessprosuite.api.controller.finance;

import com.businessprosuite.api.dto.finance.ConversionDTO;
import com.businessprosuite.api.service.finance.ConversionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conversions")
@RequiredArgsConstructor
public class ConversionController {

    private final ConversionService service;

    @GetMapping
    public ResponseEntity<List<ConversionDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConversionDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ConversionDTO> create(@RequestBody ConversionDTO dto) {
        ConversionDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConversionDTO> update(
            @PathVariable Integer id,
            @RequestBody ConversionDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}