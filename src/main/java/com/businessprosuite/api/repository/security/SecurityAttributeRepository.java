package com.businessprosuite.api.repository.security;

import com.businessprosuite.api.model.security.SecurityAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityAttributeRepository extends JpaRepository<SecurityAttribute, Integer> {
}