package com.businessprosuite.api.controller.config;

import com.businessprosuite.api.dto.config.ConfigComplianceDTO;
import com.businessprosuite.api.service.config.ConfigComplianceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/config/compliances")
public class ConfigComplianceController {

    private final ConfigComplianceService service;

    public ConfigComplianceController(ConfigComplianceService service) {
        this.service = service;
    }

    /** Listar todas las configuraciones de compliance */
    @GetMapping
    public ResponseEntity<List<ConfigComplianceDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    /** Obtener una configuración compuesta por companyId y complianceCode */
    @GetMapping("/{companyId}/{complianceCode}")
    public ResponseEntity<ConfigComplianceDTO> getById(
            @PathVariable Integer companyId,
            @PathVariable String complianceCode) {
        return ResponseEntity.ok(
                service.findById(companyId, complianceCode));
    }

    /** Crear nueva configuración */
    @PostMapping
    public ResponseEntity<ConfigComplianceDTO> create(
            @Valid @RequestBody ConfigComplianceDTO dto) {
        ConfigComplianceDTO created = service.create(dto);
        URI location = URI.create(String.format(
                "/api/config/compliances/%d/%s",
                created.getCompanyId(),
                created.getComplianceCode()));
        return ResponseEntity.created(location).body(created);
    }

    /** Actualizar existente */
    @PutMapping("/{companyId}/{complianceCode}")
    public ResponseEntity<ConfigComplianceDTO> update(
            @PathVariable Integer companyId,
            @PathVariable String complianceCode,
            @Valid @RequestBody ConfigComplianceDTO dto) {
        return ResponseEntity.ok(
                service.update(companyId, complianceCode, dto));
    }

    /** Eliminar configuración */
    @DeleteMapping("/{companyId}/{complianceCode}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer companyId,
            @PathVariable String complianceCode) {
        service.delete(companyId, complianceCode);
        return ResponseEntity.noContent().build();
    }
}
