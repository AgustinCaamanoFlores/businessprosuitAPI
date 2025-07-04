package com.businessprosuite.api.service.finance;

import com.businessprosuite.api.model.finance.AccountsPayable;
import com.businessprosuite.api.repository.finance.AccountsPayableRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountsPayableService {
    private final AccountsPayableRepository repository;

    public AccountsPayableService(AccountsPayableRepository repository) {
        this.repository = repository;
    }

    public List<AccountsPayable> findAll() {
        return repository.findAll();
    }

    public AccountsPayable save(AccountsPayable ap) {
        return repository.save(ap);
    }
}
