package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.WfHist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WfHistRepository extends JpaRepository<WfHist, Integer> {
}