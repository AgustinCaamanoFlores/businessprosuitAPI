package com.businessprosuite.api.controller.finance;

import com.businessprosuite.api.model.finance.AccountsPayable;
import com.businessprosuite.api.service.finance.AccountsPayableService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/finance/ap")
public class AccountsPayableController {
    private final AccountsPayableService service;

    public AccountsPayableController(AccountsPayableService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AccountsPayable>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<AccountsPayable> create(@RequestBody AccountsPayable ap) {
        return ResponseEntity.ok(service.save(ap));
    }
}
