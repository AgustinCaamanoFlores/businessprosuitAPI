package com.businessprosuite.api.repository.schema;

import com.businessprosuite.api.model.schema.SchemaChangelog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchemaChangelogRepository extends JpaRepository<SchemaChangelog, Integer> {
}