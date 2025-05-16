package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.security.SecAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecAttributeRepository extends JpaRepository<SecAttribute, Integer> {
}