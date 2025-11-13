package com.ars.productservice.service;

import com.ars.productservice.dto.request.product.SaveProductGroupRequest;
import com.ars.productservice.dto.request.product.SearchProductGroupRequest;
import com.dct.model.dto.response.BaseResponseDTO;

public interface ProductGroupService {
    BaseResponseDTO getAllWithPaging(SearchProductGroupRequest requestDTO);
    BaseResponseDTO getDetail(Integer id);
    BaseResponseDTO save(SaveProductGroupRequest request);
    BaseResponseDTO deleteById(Integer id);
}
