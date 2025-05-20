package com.businessprosuite.api.repository.leasing;

import com.businessprosuite.api.model.leasing.LeasingContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeasingContractRepository extends JpaRepository<LeasingContract, Integer> {
}