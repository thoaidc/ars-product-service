package com.ars.productservice.repository.impl;

import com.ars.productservice.dto.request.product.SearchProductGroupRequest;
import com.ars.productservice.dto.response.product.ProductGroupResponseDTO;
import com.ars.productservice.repository.ProductGroupRepositoryCustom;
import com.dct.config.common.SqlUtils;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ProductGroupRepositoryImpl implements ProductGroupRepositoryCustom {
    private final EntityManager entityManager;

    public ProductGroupRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Page<ProductGroupResponseDTO> getAllWithPaging(SearchProductGroupRequest requestDTO) {
        String countSql = "SELECT COUNT(*)";
        String querySql = "SELECT pg.id, pg.shop_id as shopId, pg.name, pg.code, pg.description, " +
                "pg.created_by as createdBy, pg.created_date as createdDate";
        StringBuilder whereConditions = new StringBuilder(" FROM product_group pg WHERE 1=1");
        Map<String, Object> params = new HashMap<>();
        SqlUtils.appendSqlEqualCondition(whereConditions, params, "pg.shop_id", requestDTO.getShopId());
        SqlUtils.appendDateCondition(whereConditions, params, requestDTO, "pg.created_date");
        SqlUtils.appendSqlLikeCondition(whereConditions, params, "pg.name", requestDTO.getKeyword());
        SqlUtils.setOrderByDecreasing(whereConditions, "pg.created_date");
        return SqlUtils.queryBuilder(entityManager)
                .querySql(querySql + whereConditions)
                .countQuerySql(countSql + whereConditions)
                .pageable(requestDTO.getPageable())
                .params(params)
                .getResultsWithPaging("productGroupGetWithPaging");
    }
}
