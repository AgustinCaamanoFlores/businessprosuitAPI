package com.businessprosuite.api.controller.config;

import com.businessprosuite.api.dto.config.ConfigSettingDTO;
import com.businessprosuite.api.service.config.ConfigSettingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/config/settings")
public class ConfigSettingController {

    private final ConfigSettingService service;

    public ConfigSettingController(ConfigSettingService service) {
        this.service = service;
    }

    /** Listar todos los settings */
    @GetMapping
    public ResponseEntity<List<ConfigSettingDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    /** Obtener setting por ID */
    @GetMapping("/{id}")
    public ResponseEntity<ConfigSettingDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /** Crear nuevo setting */
    @PostMapping
    public ResponseEntity<ConfigSettingDTO> create(
            @Valid @RequestBody ConfigSettingDTO dto) {
        ConfigSettingDTO created = service.create(dto);
        URI location = URI.create("/api/config/settings/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    /** Actualizar setting existente */
    @PutMapping("/{id}")
    public ResponseEntity<ConfigSettingDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody ConfigSettingDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    /** Eliminar setting por ID */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
