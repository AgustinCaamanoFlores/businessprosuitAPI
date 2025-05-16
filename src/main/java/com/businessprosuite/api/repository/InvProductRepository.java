package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.inventarios.InvProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvProductRepository extends JpaRepository<InvProduct, Integer> {
}