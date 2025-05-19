package com.businessprosuite.api.controller.config;

import com.businessprosuite.api.dto.config.ConfigCompanyDTO;
import com.businessprosuite.api.service.config.ConfigCompanyService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/config/companies")
public class ConfigCompanyController {

    private final ConfigCompanyService service;

    public ConfigCompanyController(ConfigCompanyService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ConfigCompanyDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConfigCompanyDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ConfigCompanyDTO> create(
            @Valid @RequestBody ConfigCompanyDTO dto) {
        ConfigCompanyDTO created = service.create(dto);
        URI location = URI.create(
                String.format("/api/config/companies/%d", created.getId()));
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConfigCompanyDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody ConfigCompanyDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
