package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.documentos.DocDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocDocumentRepository extends JpaRepository<DocDocument, Integer> {
}