package com.businessprosuite.api.repository.finance;

import com.businessprosuite.api.model.finance.AccountsPayable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsPayableRepository extends JpaRepository<AccountsPayable, Integer> {
}
