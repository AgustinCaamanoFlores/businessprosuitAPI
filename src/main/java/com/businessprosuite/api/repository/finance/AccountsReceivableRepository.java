package com.businessprosuite.api.repository.finance;

import com.businessprosuite.api.model.finance.AccountsReceivable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsReceivableRepository extends JpaRepository<AccountsReceivable, Integer> {
}
