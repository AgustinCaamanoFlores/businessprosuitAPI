package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.company.ComCompany;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComCompanyRepository extends JpaRepository<ComCompany, Integer> {
}