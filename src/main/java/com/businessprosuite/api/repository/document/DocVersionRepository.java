package com.businessprosuite.api.repository.document;

import com.businessprosuite.api.model.document.DocumentVersion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocVersionRepository extends JpaRepository<DocumentVersion, Integer> {
}