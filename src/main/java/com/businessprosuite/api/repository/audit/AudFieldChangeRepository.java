package com.businessprosuite.api.repository.audit;

import com.businessprosuite.api.model.audit.FieldChange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AudFieldChangeRepository extends JpaRepository<FieldChange, Integer> {
}