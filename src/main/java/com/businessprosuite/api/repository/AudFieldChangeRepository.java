package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.auditoria.AudFieldChange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AudFieldChangeRepository extends JpaRepository<AudFieldChange, Integer> {
}