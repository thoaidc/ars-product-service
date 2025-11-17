package com.ars.productservice.repository;

import com.ars.productservice.dto.request.product.SearchProductGroupRequest;
import com.ars.productservice.dto.response.product.ProductGroupDTO;
import org.springframework.data.domain.Page;

public interface ProductGroupRepositoryCustom {
    Page<ProductGroupDTO> getAllWithPaging(SearchProductGroupRequest requestDTO);
}
