package com.businessprosuite.api.controller.core;

import com.businessprosuite.api.dto.core.TestResultDTO;
import com.businessprosuite.api.service.core.TestResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test-results")
@RequiredArgsConstructor
public class TestResultController {

    private final TestResultService service;

    @GetMapping
    public ResponseEntity<List<TestResultDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestResultDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<TestResultDTO> create(@RequestBody TestResultDTO dto) {
        TestResultDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestResultDTO> update(
            @PathVariable Integer id,
            @RequestBody TestResultDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
