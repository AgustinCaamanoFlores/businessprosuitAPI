package com.businessprosuite.api.repository.hr;

import com.businessprosuite.api.model.hr.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<Worker, Integer> {
}