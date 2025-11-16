package com.ars.productservice.service;

import com.ars.productservice.dto.request.product.CreateProductRequest;
import com.ars.productservice.dto.request.product.SearchProductRequest;
import com.ars.productservice.dto.request.product.UpdateProductRequest;
import com.dct.model.dto.response.BaseResponseDTO;

public interface ProductService {
    BaseResponseDTO getAllWithPaging(SearchProductRequest request);
    BaseResponseDTO create(CreateProductRequest request);
    BaseResponseDTO update(UpdateProductRequest request);
    BaseResponseDTO delete(Integer productId);
}
