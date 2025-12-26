package com.ars.productservice.repository.impl;

import com.ars.productservice.constants.ProductConstants;
import com.ars.productservice.dto.request.product.SearchProductRequest;
import com.ars.productservice.dto.response.product.ProductDTO;
import com.ars.productservice.repository.ProductRepositoryCustom;
import com.dct.config.common.SqlUtils;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryCustom {
    private final EntityManager entityManager;

    public ProductRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Page<ProductDTO> getAllWithPaging(SearchProductRequest request) {
        String countSql = "SELECT COUNT(*) FROM product p ";
        String querySql = """
            SELECT p.id, p.shop_id as shopId, p.name, p.code, p.price, p.description,
            p.is_customizable as customizable, p.status, p.thumbnail_url as thumbnailUrl,
            p.total_sales as totalSales, p.created_by as createdBy, p.created_date as createdDate
            FROM product p
        """;

        if (Objects.nonNull(request.getGroupId())) {
            String joinSql = " JOIN product_product_group ppg ON p.id = ppg.product_id ";
            querySql += joinSql;
            countSql += joinSql;
        }

        if (Objects.nonNull(request.getCategoryId())) {
            String joinSql = " JOIN product_category pc ON p.id = pc.product_id ";
            querySql += joinSql;
            countSql += joinSql;
        }

        Map<String, Object> params = new HashMap<>();
        StringBuilder whereConditions = new StringBuilder(SqlUtils.WHERE_DEFAULT);
        SqlUtils.addEqualCondition(whereConditions, params, "p.shop_id", request.getShopId());
        SqlUtils.addEqualCondition(whereConditions, params, "ppg.product_group_id", request.getGroupId());
        SqlUtils.addEqualCondition(whereConditions, params, "pc.category_id", request.getCategoryId());
        SqlUtils.addNotEqualCondition(whereConditions, params, "p.status", ProductConstants.Status.DELETED);
        SqlUtils.addEqualCondition(whereConditions, params, "p.status", request.getStatus());
        SqlUtils.addBetweenCondition(whereConditions, params, "p.price", request.getMinPrice(), request.getMaxPrice());
        SqlUtils.addDateTimeCondition(whereConditions, params, request, "p.created_date");
        SqlUtils.addLikeCondition(whereConditions, params, request.getKeyword(), "p.code", "p.name");
        SqlUtils.setOrderByDecreasing(whereConditions, "p.id");
        return SqlUtils.queryBuilder(entityManager)
                .querySql(querySql + whereConditions)
                .countQuerySql(countSql + whereConditions)
                .pageable(request.getPageable())
                .params(params)
                .getResultsWithPaging("productGetWithPaging");
    }
}
