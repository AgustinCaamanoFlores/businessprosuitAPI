package com.businessprosuite.api.repository.schema;

import com.businessprosuite.api.model.schema.SchemaVersion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchVersionRepository extends JpaRepository<SchemaVersion, Integer> {
}