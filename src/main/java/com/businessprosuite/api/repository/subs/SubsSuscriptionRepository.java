package com.businessprosuite.api.repository.subs;

import com.businessprosuite.api.model.subs.SubsSuscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubsSuscriptionRepository extends JpaRepository<SubsSuscription, Integer> {
}