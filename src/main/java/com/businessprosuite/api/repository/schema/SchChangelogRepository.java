package com.businessprosuite.api.repository.schema;

import com.businessprosuite.api.model.schema.SchemaChangelog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchChangelogRepository extends JpaRepository<SchemaChangelog, Integer> {
}