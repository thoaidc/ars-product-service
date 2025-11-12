package com.ars.productservice.repository;

import com.ars.productservice.entity.ProductGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductGroupRepository extends JpaRepository<ProductGroup, Integer>, ProductGroupRepositoryCustom {
}
