package com.ars.productservice.repository.impl;

import com.ars.productservice.dto.request.product.SearchProductRequest;
import com.ars.productservice.dto.response.product.ProductDTO;
import com.ars.productservice.repository.CategoryRepository;
import com.ars.productservice.repository.ProductGroupRepository;
import com.ars.productservice.repository.ProductOptionRepository;
import com.ars.productservice.repository.ProductRepositoryCustom;
import com.ars.productservice.repository.VariantRepository;
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
        String countSql = "SELECT COUNT(*) FROM product p";
        String querySql = """
            SELECT p.id, p.shop_id as shopId, p.name, p.code, p.price, p.description,
            p.is_customizable as customizable,
            p.status, p.thumbnail_url as thumbnailUrl, p.original_image as originalImage,
            p.created_by as createdBy, p.created_date as createdDate
            FROM product p
        """;

        Map<String, Object> params = new HashMap<>();
        StringBuilder whereConditions = new StringBuilder(" WHERE 1=1 ");

        if (Objects.nonNull(request.getGroupId())) {
            querySql += " JOIN product_product_group ppg ON p.id = ppg.product_id";
            countSql += " JOIN product_product_group ppg ON p.id = ppg.product_id";
        }

        if (Objects.nonNull(request.getCategoryId())) {
            querySql += " JOIN product_category pc ON p.id = pc.product_id";
            countSql += " JOIN product_category pc ON p.id = pc.product_id";
        }

        SqlUtils.appendSqlEqualCondition(whereConditions, params, "p.shop_id", request.getShopId());
        SqlUtils.appendSqlEqualCondition(whereConditions, params, "p.code", request.getCode());
        SqlUtils.appendSqlEqualCondition(whereConditions, params, "p.status", request.getStatus());
        SqlUtils.appendSqlEqualCondition(whereConditions, params, "ppg.product_group_id", request.getGroupId());
        SqlUtils.appendSqlEqualCondition(whereConditions, params, "pc.category_id", request.getCategoryId());
        SqlUtils.appendSqlBetweenCondition(whereConditions, params, "p.price", request.getMinPrice(), request.getMaxPrice());
        SqlUtils.appendDateCondition(whereConditions, params, request, "p.created_date");
        SqlUtils.appendSqlLikeCondition(whereConditions, params, "p.name", request.getKeyword());
        SqlUtils.setOrderByDecreasing(whereConditions, "p.created_date");
        return SqlUtils.queryBuilder(entityManager)
                .querySql(querySql + whereConditions)
                .countQuerySql(countSql + whereConditions)
                .pageable(request.getPageable())
                .params(params)
                .getResultsWithPaging("productGetWithPaging");
    }
}
