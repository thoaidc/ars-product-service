package com.ars.productservice.repository;

import com.ars.productservice.dto.mapping.ProductCheckOrderInfo;
import com.ars.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, ProductRepositoryCustom {
    @Modifying
    @Query(value = "UPDATE product SET status = ?2 WHERE id = ?1", nativeQuery = true)
    void updateStatusById(Integer productId, String status);

    @Modifying
    @Query(value = "UPDATE product p SET p.status = ?2 WHERE p.shop_id = ?1", nativeQuery = true)
    void updateStatusByShopId(Integer shopId, String status);

    @Query(value = """
            SELECT p.id, p.shop_id as shopId, p.name, p.code, p.price, p.status, p.thumbnail_url as thumbnailUrl
            FROM product p
            WHERE p.id IN ?1
            AND p.status = 'INACTIVE'
        """,
        nativeQuery = true
    )
    List<ProductCheckOrderInfo> findOrderProductRequest(Iterable<Integer> productIds);
}
