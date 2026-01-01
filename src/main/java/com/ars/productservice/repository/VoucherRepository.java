package com.ars.productservice.repository;

import com.ars.productservice.dto.mapping.VoucherCheckOrderInfo;
import com.ars.productservice.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Integer>, VoucherRepositoryCustom {
    @Query(value = """
            SELECT v.id, v.shop_id as shopId, v.code, v.type, v.status, v.value,
                   v.date_started as dateStarted, v.date_expired as dateExpired
            FROM voucher v
            WHERE v.id IN ?1
        """,
        nativeQuery = true
    )
    List<VoucherCheckOrderInfo> findVouchersForOrderRequest(Iterable<Integer> voucherIds);

    boolean existsByShopIdAndCode(Integer shopId, String code);
}
