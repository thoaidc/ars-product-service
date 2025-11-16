package com.ars.productservice.repository;

import com.ars.productservice.dto.mapping.ProductProductGroupIdPair;
import com.ars.productservice.entity.ProductGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductGroupRepository extends JpaRepository<ProductGroup, Integer>, ProductGroupRepositoryCustom {
    @Query(value = """
            SELECT pg.id as productGroupId, ppg.product_id as productId FROM product_group pg
            JOIN product_product_group ppg ON pg.id = ppg.product_group_id
            WHERE ppg.product_id in ?
        """,
        nativeQuery = true
    )
    List<ProductProductGroupIdPair> findIdsByProductId(Iterable<Integer> productIds);
}
