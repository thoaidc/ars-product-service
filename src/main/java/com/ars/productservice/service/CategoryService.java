package com.ars.productservice.service;

import com.ars.productservice.dto.request.category.SaveCategoryRequest;
import com.dct.model.dto.request.BaseRequestDTO;
import com.dct.model.dto.response.BaseResponseDTO;

public interface CategoryService {
    BaseResponseDTO getAllWithPaging(BaseRequestDTO requestDTO);
    BaseResponseDTO getDetail(Integer id);
    BaseResponseDTO save(SaveCategoryRequest request);
    BaseResponseDTO deleteById(Integer id);
}
