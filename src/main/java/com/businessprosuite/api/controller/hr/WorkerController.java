package com.businessprosuite.api.controller.hr;

import com.businessprosuite.api.dto.hr.WorkerDTO;
import com.businessprosuite.api.service.hr.WorkerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/hr/workers")
public class WorkerController {

    private final WorkerService service;

    public WorkerController(WorkerService service) {
        this.service = service;
    }

    /** Listar todos los trabajadores */
    @GetMapping
    public ResponseEntity<List<WorkerDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    /** Obtener un trabajador por ID */
    @GetMapping("/{id}")
    public ResponseEntity<WorkerDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /** Crear un nuevo trabajador */
    @PostMapping
    public ResponseEntity<WorkerDTO> create(
            @Valid @RequestBody WorkerDTO dto) {
        WorkerDTO created = service.create(dto);
        URI location = URI.create("/api/hr/workers/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    /** Actualizar un trabajador existente */
    @PutMapping("/{id}")
    public ResponseEntity<WorkerDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody WorkerDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    /** Eliminar un trabajador por ID */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
