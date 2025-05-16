package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.configuraciones.CfgEmpresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CfgEmpresaRepository extends JpaRepository<CfgEmpresa, Integer> {
}