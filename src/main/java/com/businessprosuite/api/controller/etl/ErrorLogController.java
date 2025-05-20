package com.businessprosuite.api.controller.etl;

import com.businessprosuite.api.dto.etl.ErrorLogDTO;
import com.businessprosuite.api.service.etl.ErrorLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/etl-error-logs")
@RequiredArgsConstructor
public class ErrorLogController {

    private final ErrorLogService service;

    @GetMapping
    public ResponseEntity<List<ErrorLogDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ErrorLogDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ErrorLogDTO> create(@RequestBody ErrorLogDTO dto) {
        ErrorLogDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ErrorLogDTO> update(
            @PathVariable Integer id,
            @RequestBody ErrorLogDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
