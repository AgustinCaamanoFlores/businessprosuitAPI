package com.businessprosuite.api.repository;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface AbstractPersistableRepository<T extends AbstractPersistable<ID>, ID extends Serializable>
        extends JpaRepository<T, ID> {
}
