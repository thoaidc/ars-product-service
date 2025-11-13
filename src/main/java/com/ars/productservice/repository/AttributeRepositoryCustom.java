package com.ars.productservice.repository;

import com.ars.productservice.dto.request.attribute.SearchAttributeRequest;
import com.ars.productservice.dto.response.attribute.AttributeResponseDTO;
import org.springframework.data.domain.Page;

public interface AttributeRepositoryCustom {
    Page<AttributeResponseDTO> getAllWithPaging(SearchAttributeRequest request);
}
