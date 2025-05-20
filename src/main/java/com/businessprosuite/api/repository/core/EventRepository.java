package com.businessprosuite.api.repository.core;

import com.businessprosuite.api.model.core.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
}