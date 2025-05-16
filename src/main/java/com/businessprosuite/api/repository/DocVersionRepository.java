package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.documentos.DocVersion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocVersionRepository extends JpaRepository<DocVersion, Integer> {
}