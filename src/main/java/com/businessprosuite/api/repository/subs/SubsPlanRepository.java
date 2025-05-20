package com.businessprosuite.api.repository.subs;

import com.businessprosuite.api.model.subs.SubsPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubsPlanRepository extends JpaRepository<SubsPlan, Integer> {
}