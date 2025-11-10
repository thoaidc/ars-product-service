package com.ars.productservice.repository;

import com.ars.productservice.entity.ProductOptionAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOptionAttributeRepository extends JpaRepository<ProductOptionAttribute, Integer> {
}
