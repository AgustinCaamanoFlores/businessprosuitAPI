package com.businessprosuite.api.repository.document;

import com.businessprosuite.api.model.document.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {
}