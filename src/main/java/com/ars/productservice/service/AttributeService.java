package com.ars.productservice.service;

import com.ars.productservice.dto.request.attribute.SaveAttributeRequest;
import com.ars.productservice.dto.request.attribute.SearchAttributeRequest;
import com.dct.model.dto.response.BaseResponseDTO;

public interface AttributeService {
    BaseResponseDTO getAllWithPaging(SearchAttributeRequest requestDTO);
    BaseResponseDTO getDetail(Integer id);
    BaseResponseDTO save(SaveAttributeRequest request);
    BaseResponseDTO deleteById(Integer id);
}
