package com.businessprosuite.api.controller.inventory;

import com.businessprosuite.api.service.inventory.InventoryForecastService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory/products")
public class InventoryForecastController {
    private final InventoryForecastService forecastService;

    public InventoryForecastController(InventoryForecastService forecastService) {
        this.forecastService = forecastService;
    }

    @GetMapping("/{id}/forecast")
    public ResponseEntity<Integer> forecast(@PathVariable("id") Integer id) {
        Integer qty = forecastService.forecastReorderQuantity(id);
        return ResponseEntity.ok(qty);
    }
}
