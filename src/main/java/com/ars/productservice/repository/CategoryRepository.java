package com.ars.productservice.repository;

import com.ars.productservice.dto.mapping.ProductCategoryIdPair;
import com.ars.productservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>, CategoryRepositoryCustom {
    @Query(value = """
            SELECT c.id as categoryId, pc.product_id as productId FROM category c
            JOIN product_category pc ON c.id = pc.category_id
            WHERE pc.product_id in ?
        """,
        nativeQuery = true
    )
    List<ProductCategoryIdPair> findIdsByProductId(Iterable<Integer> productIds);
}
