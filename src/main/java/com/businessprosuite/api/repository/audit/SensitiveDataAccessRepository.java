package com.businessprosuite.api.repository.audit;

import com.businessprosuite.api.model.audit.SensitiveDataAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensitiveDataAccessRepository extends JpaRepository<SensitiveDataAccess, Integer> {
}