package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.inventarios.InvReorderRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvReorderRuleRepository extends JpaRepository<InvReorderRule, Integer> {
}