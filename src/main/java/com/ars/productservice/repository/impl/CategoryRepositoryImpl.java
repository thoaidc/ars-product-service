package com.ars.productservice.repository.impl;

import com.ars.productservice.dto.response.category.CategoryDTO;
import com.ars.productservice.repository.CategoryRepositoryCustom;
import com.dct.config.common.SqlUtils;
import com.dct.model.dto.request.BaseRequestDTO;

import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class CategoryRepositoryImpl implements CategoryRepositoryCustom {
    private final EntityManager entityManager;

    public CategoryRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Page<CategoryDTO> getAllWithPaging(BaseRequestDTO requestDTO) {
        String countSql = "SELECT COUNT(*)";
        String querySql = "SELECT c.id, c.name, c.description, c.created_by as createdBy, c.created_date as createdDate";
        StringBuilder whereConditions = new StringBuilder(" FROM category c " + SqlUtils.WHERE_DEFAULT);
        Map<String, Object> params = new HashMap<>();
        SqlUtils.addLikeCondition(whereConditions, params, requestDTO.getKeyword(), "c.name");
        SqlUtils.setOrderByDecreasing(whereConditions, "c.id");
        return SqlUtils.queryBuilder(entityManager)
                .querySql(querySql + whereConditions)
                .countQuerySql(countSql + whereConditions)
                .pageable(requestDTO.getPageable())
                .params(params)
                .getResultsWithPaging("categoryGetWithPaging");
    }
}
