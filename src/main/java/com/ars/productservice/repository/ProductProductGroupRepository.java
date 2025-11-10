package com.ars.productservice.repository;

import com.ars.productservice.entity.ProductProductGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductProductGroupRepository extends JpaRepository<ProductProductGroup, Integer> {
}
