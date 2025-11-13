package com.ars.productservice.repository.impl;

import com.ars.productservice.dto.request.attribute.SearchAttributeRequest;
import com.ars.productservice.dto.response.attribute.AttributeResponseDTO;
import com.ars.productservice.repository.AttributeRepositoryCustom;
import com.dct.config.common.SqlUtils;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class AttributeRepositoryImpl implements AttributeRepositoryCustom {
    private final EntityManager entityManager;

    public AttributeRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Page<AttributeResponseDTO> getAllWithPaging(SearchAttributeRequest request) {
        String countSql = "SELECT COUNT(*)";
        String querySql = "SELECT a.id, a.shop_id as shopId, a.name, a.created_by as createdBy, a.created_date as createdDate";
        StringBuilder whereConditions = new StringBuilder(" FROM attribute a WHERE 1=1");
        Map<String, Object> params = new HashMap<>();
        SqlUtils.appendDateCondition(whereConditions, params, request, "a.created_date");
        SqlUtils.appendSqlLikeCondition(whereConditions, params, "a.name", request.getKeyword());
        SqlUtils.setOrderByDecreasing(whereConditions, "a.created_date");
        return SqlUtils.queryBuilder(entityManager)
                .querySql(querySql + whereConditions)
                .countQuerySql(countSql + whereConditions)
                .pageable(request.getPageable())
                .params(params)
                .getResultsWithPaging("attributeGetWithPaging");
    }
}
