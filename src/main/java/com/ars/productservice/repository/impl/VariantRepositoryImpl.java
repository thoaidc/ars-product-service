package com.ars.productservice.repository.impl;

import com.ars.productservice.dto.response.product.VariantDTO;
import com.ars.productservice.repository.VariantRepositoryCustom;
import com.dct.config.common.SqlUtils;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class VariantRepositoryImpl implements VariantRepositoryCustom {
    private final EntityManager entityManager;

    public VariantRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<VariantDTO> findAllByProductId(Integer productId) {
        String querySql = """
            SELECT v.id, v.attribute_id as attributeId, v.product_id as productId,
            v.original_image as originalImage, v.thumbnail_url as thumbnailUrl, v.price,
            v.name, v.created_by as createdBy, v.created_date as createdDate
        """;
        StringBuilder whereConditions = new StringBuilder(" FROM variant v WHERE 1=1");
        Map<String, Object> params = new HashMap<>();
        SqlUtils.appendSqlEqualCondition(whereConditions, params, "v.product_id", productId);
        List<VariantDTO> variantDTOS = SqlUtils.queryBuilder(entityManager)
                .querySql(querySql + whereConditions)
                .params(params)
                .getResultsWithDTO("variantGetDetail");

        return variantDTOS;
    }
}
