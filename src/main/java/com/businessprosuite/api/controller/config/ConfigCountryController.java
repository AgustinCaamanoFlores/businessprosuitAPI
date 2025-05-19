package com.businessprosuite.api.controller.config;

import com.businessprosuite.api.dto.config.ConfigCountryDTO;
import com.businessprosuite.api.service.config.ConfigCountryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/config/countries")
public class ConfigCountryController {

    private final ConfigCountryService service;

    public ConfigCountryController(ConfigCountryService service) {
        this.service = service;
    }

    /** Listar todos los países de configuración */
    @GetMapping
    public ResponseEntity<List<ConfigCountryDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    /** Obtener un país por su código ISO */
    @GetMapping("/{code}")
    public ResponseEntity<ConfigCountryDTO> getByCode(@PathVariable String code) {
        return ResponseEntity.ok(service.findByCode(code));
    }

    /** Crear un nuevo país */
    @PostMapping
    public ResponseEntity<ConfigCountryDTO> create(
            @Valid @RequestBody ConfigCountryDTO dto) {
        ConfigCountryDTO created = service.create(dto);
        URI location = URI.create(
                String.format("/api/config/countries/%s", created.getCode()));
        return ResponseEntity.created(location).body(created);
    }

    /** Actualizar un país existente */
    @PutMapping("/{code}")
    public ResponseEntity<ConfigCountryDTO> update(
            @PathVariable String code,
            @Valid @RequestBody ConfigCountryDTO dto) {
        return ResponseEntity.ok(service.update(code, dto));
    }

    /** Eliminar un país por su código */
    @DeleteMapping("/{code}")
    public ResponseEntity<Void> delete(@PathVariable String code) {
        service.delete(code);
        return ResponseEntity.noContent().build();
    }
}
