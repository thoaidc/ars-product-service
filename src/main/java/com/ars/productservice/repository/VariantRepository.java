package com.ars.productservice.repository;

import com.ars.productservice.entity.Variant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VariantRepository extends JpaRepository<Variant, Integer>, VariantRepositoryCustom {
    @Query(value = "SELECT * FROM variant v WHERE v.product_id = ?", nativeQuery = true)
    List<Variant> findAllByProductId(Integer productId);
}
