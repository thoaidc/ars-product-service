package com.ars.productservice.repository;

import com.ars.productservice.entity.VariantOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface VariantOptionRepository extends JpaRepository<VariantOption, Integer> {
    void deleteAllByVariantIdIn(Collection<Integer> variantIds);
}
