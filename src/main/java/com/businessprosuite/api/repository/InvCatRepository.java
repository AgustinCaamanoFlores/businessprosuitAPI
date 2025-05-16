package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.inventarios.InvCat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvCatRepository extends JpaRepository<InvCat, Integer> {
}