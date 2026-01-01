package com.ars.productservice.repository.impl;

import com.ars.productservice.constants.VoucherConstants;
import com.ars.productservice.dto.request.voucher.SearchVoucherRequest;
import com.ars.productservice.dto.response.voucher.VoucherDTO;
import com.ars.productservice.repository.VoucherRepositoryCustom;
import com.dct.config.common.SqlUtils;
import com.dct.model.common.DateUtils;
import com.dct.model.constants.BaseDatetimeConstants;

import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class VoucherRepositoryImpl implements VoucherRepositoryCustom {
    private final EntityManager entityManager;

    public VoucherRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<VoucherDTO> getVoucherForUserOrder(SearchVoucherRequest request) {
        String querySql = """
            SELECT v.id, v.shop_id as shopId,
            v.name, v.code, v.type, v.status, v.value,
            v.date_started as dateStarted, v.date_expired as dateExpired
            FROM voucher v
        """;
        Map<String, Object> params = new HashMap<>();
        StringBuilder whereConditions = new StringBuilder(SqlUtils.WHERE_DEFAULT);
        String now = DateUtils.now().toString(BaseDatetimeConstants.Formatter.YYYY_MM_DD_NORMALIZED);
        Integer nowInteger = Integer.valueOf(now);
        SqlUtils.addInCondition(whereConditions, params, "v.shop_id", request.getShopIds());
        SqlUtils.addEqualCondition(whereConditions, params, "v.status", VoucherConstants.Status.ACTIVE);
        SqlUtils.addGreaterThanOrEqualCondition(whereConditions, params, "v.date_expired", nowInteger);
        SqlUtils.setOrderByDecreasing(whereConditions, "v.id");
        return SqlUtils.queryBuilder(entityManager)
                .querySql(querySql + whereConditions)
                .pageable(request.getPageable())
                .params(params)
                .getResults("voucherGetAllForUser");
    }

    @Override
    public Page<VoucherDTO> getWithPaging(SearchVoucherRequest request) {
        String countSql = "SELECT COUNT(*) FROM voucher v ";
        String querySql = """
            SELECT v.id, v.shop_id as shopId,
            v.name, v.code, v.type, v.status, v.value,
            v.date_started as dateStarted, v.date_expired as dateExpired,
            v.created_date as createdDate, v.last_modified_date as lastModifiedDate
            FROM voucher v
        """;
        Map<String, Object> params = new HashMap<>();
        StringBuilder whereConditions = new StringBuilder(SqlUtils.WHERE_DEFAULT);
        SqlUtils.addEqualCondition(whereConditions, params, "v.shopId", request.getShopId());
        SqlUtils.addEqualCondition(whereConditions, params, "v.code", request.getCode());
        SqlUtils.addEqualCondition(whereConditions, params, "v.type", request.getType());
        SqlUtils.addEqualCondition(whereConditions, params, "v.status", request.getActive());
        SqlUtils.addGreaterThanOrEqualCondition(
            whereConditions,
            params,
            "v.date_started",
            request.getDateStarted()
        );
        SqlUtils.addLessThanOrEqualCondition(
            whereConditions,
            params,
            "v.date_expired",
            request.getDateExpired()
        );
        SqlUtils.addLikeCondition(whereConditions, params, request.getKeyword(), "v.name");
        SqlUtils.setOrderByDecreasing(whereConditions, "v.id");
        return SqlUtils.queryBuilder(entityManager)
                .querySql(querySql + whereConditions)
                .countQuerySql(countSql + whereConditions)
                .pageable(request.getPageable())
                .params(params)
                .getResultsWithPaging("voucherGetWithPaging");
    }
}
