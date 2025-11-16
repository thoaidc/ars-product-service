package com.ars.productservice.repository;

import com.ars.productservice.dto.request.product.SearchProductRequest;
import com.ars.productservice.dto.response.product.ProductDTO;
import org.springframework.data.domain.Page;

public interface ProductRepositoryCustom {
    Page<ProductDTO> getAllWithPaging(SearchProductRequest request);
}
