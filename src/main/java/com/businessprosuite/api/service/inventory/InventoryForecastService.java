package com.businessprosuite.api.service.inventory;

import com.businessprosuite.api.model.inventory.InventoryProduct;
import com.businessprosuite.api.model.inventory.InventoryReorderRule;
import com.businessprosuite.api.repository.inventory.InventoryProductRepository;
import com.businessprosuite.api.repository.inventory.InventoryReorderRuleRepository;
import org.springframework.stereotype.Service;

@Service
public class InventoryForecastService {
    private final InventoryProductRepository productRepository;
    private final InventoryReorderRuleRepository ruleRepository;

    public InventoryForecastService(InventoryProductRepository productRepository,
                                    InventoryReorderRuleRepository ruleRepository) {
        this.productRepository = productRepository;
        this.ruleRepository = ruleRepository;
    }

    public Integer forecastReorderQuantity(Integer productId) {
        InventoryProduct product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        InventoryReorderRule rule = ruleRepository.findFirstByProd_Id(productId);
        if (rule != null && product.getInvProdStock() <= rule.getThreshold()) {
            return rule.getOrderQty();
        }
        return 0;
    }
}
