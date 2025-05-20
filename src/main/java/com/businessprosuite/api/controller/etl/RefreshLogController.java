package com.businessprosuite.api.controller.etl;

import com.businessprosuite.api.dto.etl.RefreshLogDTO;
import com.businessprosuite.api.service.etl.RefreshLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/etl-refresh-logs")
@RequiredArgsConstructor
public class RefreshLogController {

    private final RefreshLogService service;

    @GetMapping
    public ResponseEntity<List<RefreshLogDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RefreshLogDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<RefreshLogDTO> create(@RequestBody RefreshLogDTO dto) {
        RefreshLogDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RefreshLogDTO> update(
            @PathVariable Integer id,
            @RequestBody RefreshLogDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
