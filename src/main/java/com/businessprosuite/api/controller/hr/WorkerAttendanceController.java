package com.businessprosuite.api.controller.hr;

import com.businessprosuite.api.dto.hr.WorkerAttendanceDTO;
import com.businessprosuite.api.service.hr.WorkerAttendanceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/hr/attendances")
public class WorkerAttendanceController {

    private final WorkerAttendanceService service;

    public WorkerAttendanceController(WorkerAttendanceService service) {
        this.service = service;
    }

    /** Listar todas las asistencias */
    @GetMapping
    public ResponseEntity<List<WorkerAttendanceDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    /** Obtener asistencia por ID */
    @GetMapping("/{id}")
    public ResponseEntity<WorkerAttendanceDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /** Registrar nueva asistencia */
    @PostMapping
    public ResponseEntity<WorkerAttendanceDTO> create(
            @Valid @RequestBody WorkerAttendanceDTO dto) {
        WorkerAttendanceDTO created = service.create(dto);
        URI location = URI.create("/api/hr/attendances/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    /** Actualizar asistencia existente */
    @PutMapping("/{id}")
    public ResponseEntity<WorkerAttendanceDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody WorkerAttendanceDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    /** Eliminar asistencia por ID */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
