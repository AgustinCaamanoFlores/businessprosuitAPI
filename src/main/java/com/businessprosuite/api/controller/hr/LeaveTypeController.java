package com.businessprosuite.api.controller.hr;

import com.businessprosuite.api.dto.hr.LeaveTypeDTO;
import com.businessprosuite.api.service.hr.LeaveTypeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/hr/leave-types")
public class LeaveTypeController {

    private final LeaveTypeService service;

    public LeaveTypeController(LeaveTypeService service) {
        this.service = service;
    }

    /** Listar todos los tipos de permiso */
    @GetMapping
    public ResponseEntity<List<LeaveTypeDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    /** Obtener un tipo de permiso por ID */
    @GetMapping("/{id}")
    public ResponseEntity<LeaveTypeDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /** Crear un nuevo tipo de permiso */
    @PostMapping
    public ResponseEntity<LeaveTypeDTO> create(
            @Valid @RequestBody LeaveTypeDTO dto) {
        LeaveTypeDTO created = service.create(dto);
        URI location = URI.create("/api/hr/leave-types/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    /** Actualizar un tipo de permiso existente */
    @PutMapping("/{id}")
    public ResponseEntity<LeaveTypeDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody LeaveTypeDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    /** Eliminar un tipo de permiso por ID */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
