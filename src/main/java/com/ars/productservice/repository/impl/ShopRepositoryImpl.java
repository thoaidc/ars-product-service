package com.ars.productservice.repository.impl;

import com.ars.productservice.dto.response.shop.ShopDTO;
import com.ars.productservice.repository.ShopRepositoryCustom;
import com.dct.config.common.SqlUtils;
import com.dct.model.dto.request.BaseRequestDTO;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ShopRepositoryImpl implements ShopRepositoryCustom {
    private final EntityManager entityManager;

    public ShopRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Page<ShopDTO> getShopsWithPaging(BaseRequestDTO request) {
        String countSql = "SELECT COUNT(*) FROM shop s ";
        String querySql = "SELECT s.id, s.owner_id as ownerId, s.name, s.status, s.created_date as createdDate FROM shop s";
        Map<String, Object> params = new HashMap<>();
        StringBuilder whereConditions = new StringBuilder(SqlUtils.WHERE_DEFAULT);
        SqlUtils.addEqualCondition(whereConditions, params, "s.status", request.getStatus());
        SqlUtils.addDateTimeCondition(whereConditions, params, request, "s.created_date");
        SqlUtils.addLikeCondition(whereConditions, params, request.getKeyword(), "s.name");
        SqlUtils.setOrderByDecreasing(whereConditions, "s.id");
        return SqlUtils.queryBuilder(entityManager)
                .querySql(querySql + whereConditions)
                .countQuerySql(countSql + whereConditions)
                .pageable(request.getPageable())
                .params(params)
                .getResultsWithPaging("shopGetWithPaging");
    }
}
