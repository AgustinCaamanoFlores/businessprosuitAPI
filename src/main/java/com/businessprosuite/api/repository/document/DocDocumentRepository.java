package com.businessprosuite.api.repository.document;

import com.businessprosuite.api.model.document.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocDocumentRepository extends JpaRepository<Document, Integer> {
}