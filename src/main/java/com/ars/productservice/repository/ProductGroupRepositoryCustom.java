package com.ars.productservice.repository;

import com.ars.productservice.dto.response.ProductGroupResponseDTO;
import com.dct.model.dto.request.BaseRequestDTO;

import java.util.List;

public interface ProductGroupRepositoryCustom {
    List<ProductGroupResponseDTO> getAllWithPaging(BaseRequestDTO requestDTO);
}
