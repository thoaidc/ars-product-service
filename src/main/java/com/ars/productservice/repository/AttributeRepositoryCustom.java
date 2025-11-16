package com.ars.productservice.repository;

import com.ars.productservice.dto.request.attribute.SearchAttributeRequest;
import com.ars.productservice.dto.response.attribute.AttributeDTO;
import org.springframework.data.domain.Page;

public interface AttributeRepositoryCustom {
    Page<AttributeDTO> getAllWithPaging(SearchAttributeRequest request);
}
