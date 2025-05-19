package com.businessprosuite.api.controller.asset;

import com.businessprosuite.api.dto.asset.AssetMaintenanceDTO;
import com.businessprosuite.api.service.asset.AssetMaintenanceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/asset/maintenances")
public class AssetMaintenanceController {

    private final AssetMaintenanceService service;

    public AssetMaintenanceController(AssetMaintenanceService service) {
        this.service = service;
    }

    /** Listar todos los mantenimientos */
    @GetMapping
    public ResponseEntity<List<AssetMaintenanceDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    /** Obtener mantenimiento por ID */
    @GetMapping("/{id}")
    public ResponseEntity<AssetMaintenanceDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /** Crear nuevo mantenimiento */
    @PostMapping
    public ResponseEntity<AssetMaintenanceDTO> create(
            @Valid @RequestBody AssetMaintenanceDTO dto) {
        AssetMaintenanceDTO created = service.create(dto);
        URI location = URI.create("/api/asset/maintenances/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    /** Actualizar mantenimiento existente */
    @PutMapping("/{id}")
    public ResponseEntity<AssetMaintenanceDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody AssetMaintenanceDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    /** Eliminar mantenimiento por ID */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
