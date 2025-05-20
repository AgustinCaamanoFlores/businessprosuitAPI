package com.businessprosuite.api.controller.error;

import com.businessprosuite.api.dto.error.ProcErrorLogDTO;
import com.businessprosuite.api.service.error.ProcErrorLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proc-error-logs")
@RequiredArgsConstructor
public class ProcErrorLogController {

    private final ProcErrorLogService service;

    @GetMapping
    public ResponseEntity<List<ProcErrorLogDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProcErrorLogDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProcErrorLogDTO> create(@RequestBody ProcErrorLogDTO dto) {
        ProcErrorLogDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProcErrorLogDTO> update(
            @PathVariable Integer id,
            @RequestBody ProcErrorLogDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
