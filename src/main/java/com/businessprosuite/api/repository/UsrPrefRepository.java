package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.user.UsrPref;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsrPrefRepository extends JpaRepository<UsrPref, Integer> {
}