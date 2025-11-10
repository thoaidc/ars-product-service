package com.ars.productservice.service;

import com.ars.productservice.dto.request.SaveProductGroupRequest;
import com.dct.model.dto.request.BaseRequestDTO;
import com.dct.model.dto.response.BaseResponseDTO;

public interface ProductGroupService {
    BaseResponseDTO getAllWithPaging(BaseRequestDTO requestDTO);
    BaseResponseDTO getDetail(Integer id);
    BaseResponseDTO save(SaveProductGroupRequest request);
    BaseResponseDTO deleteById(Integer id);
}
