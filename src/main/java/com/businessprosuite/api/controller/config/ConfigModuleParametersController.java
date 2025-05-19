package com.businessprosuite.api.controller.config;

import com.businessprosuite.api.dto.config.ConfigModuleParametersDTO;
import com.businessprosuite.api.service.config.ConfigModuleParametersService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/config/module-params")
public class ConfigModuleParametersController {

    private final ConfigModuleParametersService service;

    public ConfigModuleParametersController(ConfigModuleParametersService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ConfigModuleParametersDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConfigModuleParametersDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ConfigModuleParametersDTO> create(
            @Valid @RequestBody ConfigModuleParametersDTO dto) {
        ConfigModuleParametersDTO created = service.create(dto);
        URI location = URI.create("/api/config/module-params/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConfigModuleParametersDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody ConfigModuleParametersDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
