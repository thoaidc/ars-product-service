package com.ars.productservice.repository;

import com.ars.productservice.entity.OutBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutBoxRepository extends JpaRepository<OutBox, Integer> {
    @Query(value = "SELECT * FROM outbox WHERE status = ? ORDER BY id DESC LIMIT 10", nativeQuery = true)
    List<OutBox> findTopOutBoxesByStatus(String status);
}
