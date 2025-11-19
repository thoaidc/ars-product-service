package com.ars.productservice.repository;

import com.ars.productservice.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOption, Integer> {
    List<ProductOption> findAllByProductId(Integer productId);
}
