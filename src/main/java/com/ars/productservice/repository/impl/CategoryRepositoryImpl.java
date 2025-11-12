package com.ars.productservice.repository.impl;

import com.ars.productservice.dto.response.CategoryResponseDTO;
import com.ars.productservice.repository.CategoryRepositoryCustom;
import com.dct.model.dto.request.BaseRequestDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class CategoryRepositoryImpl implements CategoryRepositoryCustom {
    private final EntityManager entityManager;

    public CategoryRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<CategoryResponseDTO> getAllWithPaging(BaseRequestDTO requestDTO) {
        StringBuilder sql = new StringBuilder("SELECT c.id, c.name, c.code FROM category c WHERE 1=1");
        Map<String, Object> params = new HashMap<>();

        if (Objects.nonNull(requestDTO.getFromDateSearch()) && Objects.nonNull(requestDTO.getToDateSearch())) {
            sql.append(" AND c.createdBy BETWEEN :fromDate AND :toDate");
            params.put("fromDate", requestDTO.getFromDateSearch());
            params.put("toDate", requestDTO.getToDateSearch());
        }

        Query query = entityManager.createNativeQuery(sql.toString(), "categoryGetWithPaging");
        params.forEach(query::setParameter);
        //noinspection unchecked
        return (List<CategoryResponseDTO>) query.getResultList();
    }
}
