package com.businessprosuite.api.service.finance;

import com.businessprosuite.api.model.finance.AccountsReceivable;
import com.businessprosuite.api.repository.finance.AccountsReceivableRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountsReceivableService {
    private final AccountsReceivableRepository repository;

    public AccountsReceivableService(AccountsReceivableRepository repository) {
        this.repository = repository;
    }

    public List<AccountsReceivable> findAll() {
        return repository.findAll();
    }

    public AccountsReceivable save(AccountsReceivable ar) {
        return repository.save(ar);
    }
}
