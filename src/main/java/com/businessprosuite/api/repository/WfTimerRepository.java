package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.WfTimer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WfTimerRepository extends JpaRepository<WfTimer, Integer> {
}