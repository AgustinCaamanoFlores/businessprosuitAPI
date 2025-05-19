package com.businessprosuite.api.controller.hr;

import com.businessprosuite.api.dto.hr.CompanyDeviceDTO;
import com.businessprosuite.api.service.hr.CompanyDeviceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/hr/devices")
public class CompanyDeviceController {

    private final CompanyDeviceService service;

    public CompanyDeviceController(CompanyDeviceService service) {
        this.service = service;
    }

    /** Listar todos los dispositivos de empresa */
    @GetMapping
    public ResponseEntity<List<CompanyDeviceDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    /** Obtener un dispositivo por ID */
    @GetMapping("/{id}")
    public ResponseEntity<CompanyDeviceDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /** Crear un nuevo dispositivo */
    @PostMapping
    public ResponseEntity<CompanyDeviceDTO> create(
            @Valid @RequestBody CompanyDeviceDTO dto) {
        CompanyDeviceDTO created = service.create(dto);
        URI location = URI.create("/api/hr/devices/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    /** Actualizar un dispositivo existente */
    @PutMapping("/{id}")
    public ResponseEntity<CompanyDeviceDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody CompanyDeviceDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    /** Eliminar un dispositivo por ID */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
