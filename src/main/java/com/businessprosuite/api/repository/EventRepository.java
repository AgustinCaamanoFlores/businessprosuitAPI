package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
}