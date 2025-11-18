package com.ars.productservice.repository;

import com.ars.productservice.dto.mapping.ProductOptionMapping;
import com.ars.productservice.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOption, Integer> {
    @Query(value = """
            SELECT po.id
                   po.productId
                   po.name
                   po.type
                   po.topPercentage
                   po.leftPercentage
                   po.widthPercentage
                   po.heightPercentage
                   po.description
            FROM product_option po
            JOIN variant_option vo ON vo.product_option_id = po.id
            WHERE vo.variant_id = ?
        """,
        nativeQuery = true
    )
    List<ProductOptionMapping> findAllByVariantIds(Iterable<Integer> variantIds);
}
