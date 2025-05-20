package com.businessprosuite.api.repository.document;

import com.businessprosuite.api.model.document.DocumentVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentVersionRepository extends JpaRepository<DocumentVersion, Integer> {
}