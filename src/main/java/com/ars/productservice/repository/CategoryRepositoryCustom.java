package com.ars.productservice.repository;

import com.ars.productservice.dto.response.CategoryResponseDTO;
import com.dct.model.dto.request.BaseRequestDTO;

import java.util.List;

public interface CategoryRepositoryCustom {
    List<CategoryResponseDTO> getAllWithPaging(BaseRequestDTO requestDTO);
}
