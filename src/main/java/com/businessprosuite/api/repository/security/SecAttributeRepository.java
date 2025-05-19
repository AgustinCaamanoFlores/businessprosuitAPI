package com.businessprosuite.api.repository.security;

import com.businessprosuite.api.model.security.SecurityAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecAttributeRepository extends JpaRepository<SecurityAttribute, Integer> {
}