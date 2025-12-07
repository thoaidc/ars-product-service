package com.ars.productservice.repository;

import com.ars.productservice.dto.mapping.ShopInfoLogin;
import com.ars.productservice.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Integer>, ShopRepositoryCustom {
    @Query(value = "SELECT s.name as shopName, s.id as shopId FROM shop s WHERE s.user_id = ?", nativeQuery = true)
    Optional<ShopInfoLogin> findShopInfoLoginByUserId(Integer userId);

    @Modifying
    @Query(value = "UPDATE shop SET status = ?2 WHERE id = ?1", nativeQuery = true)
    void updateShopStatusById(Integer shopId, String status);

    boolean existsByOwnerIdAndName(Integer ownerId, String name);
}
