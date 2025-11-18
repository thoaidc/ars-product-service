package com.ars.productservice.repository.impl;

import com.ars.productservice.repository.VariantRepositoryCustom;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class VariantRepositoryImpl implements VariantRepositoryCustom {
    private final EntityManager entityManager;

    public VariantRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
