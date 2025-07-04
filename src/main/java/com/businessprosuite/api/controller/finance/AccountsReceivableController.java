package com.businessprosuite.api.controller.finance;

import com.businessprosuite.api.model.finance.AccountsReceivable;
import com.businessprosuite.api.service.finance.AccountsReceivableService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/finance/ar")
public class AccountsReceivableController {
    private final AccountsReceivableService service;

    public AccountsReceivableController(AccountsReceivableService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AccountsReceivable>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<AccountsReceivable> create(@RequestBody AccountsReceivable ar) {
        return ResponseEntity.ok(service.save(ar));
    }
}
