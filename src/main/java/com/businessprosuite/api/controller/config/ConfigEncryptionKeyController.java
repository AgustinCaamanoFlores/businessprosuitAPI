package com.businessprosuite.api.controller.config;

import com.businessprosuite.api.dto.config.ConfigEncryptionKeyDTO;
import com.businessprosuite.api.service.config.ConfigEncryptionKeyService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/config/encryption-keys")
public class ConfigEncryptionKeyController {

    private final ConfigEncryptionKeyService service;

    public ConfigEncryptionKeyController(ConfigEncryptionKeyService service) {
        this.service = service;
    }

    /** Listar todas las claves de cifrado */
    @GetMapping
    public ResponseEntity<List<ConfigEncryptionKeyDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    /** Obtener una clave por su ID */
    @GetMapping("/{id}")
    public ResponseEntity<ConfigEncryptionKeyDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /** Crear una nueva clave de cifrado */
    @PostMapping
    public ResponseEntity<ConfigEncryptionKeyDTO> create(
            @Valid @RequestBody ConfigEncryptionKeyDTO dto) {
        ConfigEncryptionKeyDTO created = service.create(dto);
        URI location = URI.create("/api/config/encryption-keys/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    /** Actualizar una clave existente */
    @PutMapping("/{id}")
    public ResponseEntity<ConfigEncryptionKeyDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody ConfigEncryptionKeyDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    /** Eliminar una clave */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
