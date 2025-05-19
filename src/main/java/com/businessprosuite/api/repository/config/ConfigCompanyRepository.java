package com.businessprosuite.api.repository.config;

import com.businessprosuite.api.model.config.ConfigCompany;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigCompanyRepository extends JpaRepository<ConfigCompany, Integer> {
}