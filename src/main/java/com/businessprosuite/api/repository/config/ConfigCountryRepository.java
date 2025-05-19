package com.businessprosuite.api.repository.config;

import com.businessprosuite.api.model.config.ConfigCountry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigCountryRepository extends JpaRepository<ConfigCountry, String> {
}