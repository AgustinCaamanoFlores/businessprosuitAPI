package com.businessprosuite.api.controller.hr;

import com.businessprosuite.api.dto.hr.ShiftDTO;
import com.businessprosuite.api.service.hr.ShiftService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/hr/shifts")
public class ShiftController {

    private final ShiftService service;

    public ShiftController(ShiftService service) {
        this.service = service;
    }

    /** Listar todos los turnos */
    @GetMapping
    public ResponseEntity<List<ShiftDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    /** Obtener un turno por ID */
    @GetMapping("/{id}")
    public ResponseEntity<ShiftDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /** Crear un nuevo turno */
    @PostMapping
    public ResponseEntity<ShiftDTO> create(@Valid @RequestBody ShiftDTO dto) {
        ShiftDTO created = service.create(dto);
        URI location = URI.create("/api/hr/shifts/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    /** Actualizar un turno existente */
    @PutMapping("/{id}")
    public ResponseEntity<ShiftDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody ShiftDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    /** Eliminar un turno por ID */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
