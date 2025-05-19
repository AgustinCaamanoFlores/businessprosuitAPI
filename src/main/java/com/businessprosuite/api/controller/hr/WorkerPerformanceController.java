package com.businessprosuite.api.controller.hr;

import com.businessprosuite.api.dto.hr.WorkerPerformanceDTO;
import com.businessprosuite.api.service.hr.WorkerPerformanceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/hr/performances")
public class WorkerPerformanceController {

    private final WorkerPerformanceService service;

    public WorkerPerformanceController(WorkerPerformanceService service) {
        this.service = service;
    }

    /** Listar todas las evaluaciones de rendimiento */
    @GetMapping
    public ResponseEntity<List<WorkerPerformanceDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    /** Obtener una evaluación por ID */
    @GetMapping("/{id}")
    public ResponseEntity<WorkerPerformanceDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /** Crear nueva evaluación */
    @PostMapping
    public ResponseEntity<WorkerPerformanceDTO> create(
            @Valid @RequestBody WorkerPerformanceDTO dto) {
        WorkerPerformanceDTO created = service.create(dto);
        URI location = URI.create("/api/hr/performances/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    /** Actualizar evaluación existente */
    @PutMapping("/{id}")
    public ResponseEntity<WorkerPerformanceDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody WorkerPerformanceDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    /** Eliminar evaluación por ID */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
