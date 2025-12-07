package com.ars.productservice.repository;

import com.ars.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, ProductRepositoryCustom {
    @Modifying
    @Query(value = "UPDATE product SET status = ?2 WHERE id = ?1", nativeQuery = true)
    void updateStatusById(Integer productId, String status);

    @Modifying
    @Query(value = "UPDATE product p SET p.status = ?2 WHERE p.shop_id = ?1", nativeQuery = true)
    void updateStatusByShopId(Integer shopId, String status);
}
