package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.NotifQueue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotifQueueRepository extends JpaRepository<NotifQueue, Integer> {
}