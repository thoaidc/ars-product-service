package com.ars.productservice.repository.impl;

import com.ars.productservice.dto.response.ProductGroupResponseDTO;
import com.ars.productservice.repository.ProductGroupRepositoryCustom;
import com.dct.model.dto.request.BaseRequestDTO;

import java.util.List;

public class ProductGroupRepositoryImpl implements ProductGroupRepositoryCustom {
    @Override
    public List<ProductGroupResponseDTO> getAllWithPaging(BaseRequestDTO requestDTO) {
        return List.of();
    }
}
