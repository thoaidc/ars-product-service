package com.ars.productservice.repository;

import com.ars.productservice.entity.VariantOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VariantOptionRepository extends JpaRepository<VariantOption, Integer> {
}
