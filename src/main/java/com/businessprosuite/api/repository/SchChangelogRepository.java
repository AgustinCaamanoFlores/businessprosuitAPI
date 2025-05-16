package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.SchChangelog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchChangelogRepository extends JpaRepository<SchChangelog, Integer> {
}