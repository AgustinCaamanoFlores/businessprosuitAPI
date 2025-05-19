// src/main/java/com/businessprosuite/api/controller/company/BranchController.java
package com.businessprosuite.api.controller.company;

import com.businessprosuite.api.dto.company.CompanyBranchDTO;
import com.businessprosuite.api.service.company.CompanyBranchService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/branches")
public class CompanyBranchController {

    private final CompanyBranchService service;
    public CompanyBranchController(CompanyBranchService service) {
        this.service = service;
    }

    /** Listar todas las sucursales */
    @GetMapping
    public ResponseEntity<List<CompanyBranchDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    /** Obtener sucursal por ID */
    @GetMapping("/{id}")
    public ResponseEntity<CompanyBranchDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /** Crear nueva sucursal */
    @PostMapping
    public ResponseEntity<CompanyBranchDTO> create(
            @Valid @RequestBody CompanyBranchDTO dto) {
        CompanyBranchDTO created = service.create(dto);
        URI location = URI.create("/api/branches/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    /** Actualizar sucursal existente */
    @PutMapping("/{id}")
    public ResponseEntity<CompanyBranchDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody CompanyBranchDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    /** Eliminar sucursal */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
