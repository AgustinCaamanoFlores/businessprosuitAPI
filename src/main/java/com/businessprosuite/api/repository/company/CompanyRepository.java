package com.businessprosuite.api.repository.company;

import com.businessprosuite.api.model.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
}