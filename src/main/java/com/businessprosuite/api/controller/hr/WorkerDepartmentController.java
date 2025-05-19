package com.businessprosuite.api.controller.hr;

import com.businessprosuite.api.dto.hr.WorkerDepartmentDTO;
import com.businessprosuite.api.service.hr.WorkerDepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/hr/departments")
public class WorkerDepartmentController {

    private final WorkerDepartmentService service;

    public WorkerDepartmentController(WorkerDepartmentService service) {
        this.service = service;
    }

    /** Listar todos los departamentos */
    @GetMapping
    public ResponseEntity<List<WorkerDepartmentDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    /** Obtener departamento por ID */
    @GetMapping("/{id}")
    public ResponseEntity<WorkerDepartmentDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /** Crear un nuevo departamento */
    @PostMapping
    public ResponseEntity<WorkerDepartmentDTO> create(
            @Valid @RequestBody WorkerDepartmentDTO dto) {
        WorkerDepartmentDTO created = service.create(dto);
        URI location = URI.create("/api/hr/departments/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    /** Actualizar un departamento existente */
    @PutMapping("/{id}")
    public ResponseEntity<WorkerDepartmentDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody WorkerDepartmentDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    /** Eliminar un departamento por ID */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
