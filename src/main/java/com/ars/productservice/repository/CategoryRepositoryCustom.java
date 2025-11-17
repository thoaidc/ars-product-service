package com.ars.productservice.repository;

import com.ars.productservice.dto.response.category.CategoryDTO;
import com.dct.model.dto.request.BaseRequestDTO;
import org.springframework.data.domain.Page;

public interface CategoryRepositoryCustom {
    Page<CategoryDTO> getAllWithPaging(BaseRequestDTO requestDTO);
}
