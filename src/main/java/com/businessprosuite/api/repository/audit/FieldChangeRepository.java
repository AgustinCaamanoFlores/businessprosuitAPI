package com.businessprosuite.api.repository.audit;

import com.businessprosuite.api.model.audit.FieldChange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldChangeRepository extends JpaRepository<FieldChange, Integer> {
}