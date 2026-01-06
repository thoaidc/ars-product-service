package com.ars.productservice.repository.impl;

import com.ars.productservice.dto.request.review.CheckCustomerReviewRequest;
import com.ars.productservice.dto.request.review.SearchReviewRequest;
import com.ars.productservice.dto.response.review.ReviewDTO;
import com.ars.productservice.repository.ReviewRepositoryCustom;
import com.dct.config.common.SqlUtils;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {
    private final EntityManager entityManager;

    public ReviewRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Page<ReviewDTO> getAllWithPaging(SearchReviewRequest request) {
        String countSql = "SELECT COUNT(*) FROM review r ";
        String querySql = """
            SELECT r.id, r.product_id as productId, r.shop_id as shopId,
            r.customer_id as customerId,
            r.customer_name as customerName,
            r.content, r.image, r.created_date as createdDate
            FROM review r
        """;
        Map<String, Object> params = new HashMap<>();
        StringBuilder whereConditions = new StringBuilder(SqlUtils.WHERE_DEFAULT);
        SqlUtils.addEqualCondition(whereConditions, params, "r.shop_id", request.getShopId());
        SqlUtils.addEqualCondition(whereConditions, params, "r.product_id", request.getProductId());
        SqlUtils.addEqualCondition(whereConditions, params, "r.customer_id", request.getCustomerId());
        SqlUtils.setOrderByDecreasing(whereConditions, "r.id");
        return SqlUtils.queryBuilder(entityManager)
                .querySql(querySql + whereConditions)
                .countQuerySql(countSql + whereConditions)
                .pageable(request.getPageable())
                .params(params)
                .getResultsWithPaging("reviewGetWithPaging");
    }

    @Override
    public List<ReviewDTO> getAllByCustomerIdAndProductIds(CheckCustomerReviewRequest request) {
        String querySql = """
            SELECT r.id, r.product_id as productId, r.shop_id as shopId,
            r.customer_id as customerId,
            r.customer_name as customerName,
            r.content, r.image, r.created_date as createdDate
            FROM review r
        """;
        Map<String, Object> params = new HashMap<>();
        StringBuilder whereConditions = new StringBuilder(SqlUtils.WHERE_DEFAULT);
        SqlUtils.addInCondition(whereConditions, params, "r.product_id", request.getProductIds());
        SqlUtils.addEqualCondition(whereConditions, params, "r.customer_id", request.getCustomerId());
        SqlUtils.setOrderByDecreasing(whereConditions, "r.id");
        return SqlUtils.queryBuilder(entityManager)
                .querySql(querySql + whereConditions)
                .params(params)
                .getResults("reviewGetWithPaging");
    }
}
