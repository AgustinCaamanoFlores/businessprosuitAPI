package com.businessprosuite.api.repository.schema;

import com.businessprosuite.api.model.schema.SchemaVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchemaVersionRepository extends JpaRepository<SchemaVersion, Integer> {
}