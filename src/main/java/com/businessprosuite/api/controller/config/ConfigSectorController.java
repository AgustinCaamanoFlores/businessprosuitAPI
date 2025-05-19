package com.businessprosuite.api.controller.config;

import com.businessprosuite.api.dto.config.ConfigSectorDTO;
import com.businessprosuite.api.service.config.ConfigSectorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/config/sectors")
public class ConfigSectorController {

    private final ConfigSectorService service;

    public ConfigSectorController(ConfigSectorService service) {
        this.service = service;
    }

    /** Listar todos los sectores */
    @GetMapping
    public ResponseEntity<List<ConfigSectorDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    /** Obtener sector por ID */
    @GetMapping("/{id}")
    public ResponseEntity<ConfigSectorDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /** Crear un nuevo sector */
    @PostMapping
    public ResponseEntity<ConfigSectorDTO> create(
            @Valid @RequestBody ConfigSectorDTO dto) {
        ConfigSectorDTO created = service.create(dto);
        URI location = URI.create("/api/config/sectors/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    /** Actualizar sector existente */
    @PutMapping("/{id}")
    public ResponseEntity<ConfigSectorDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody ConfigSectorDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    /** Eliminar sector por ID */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
