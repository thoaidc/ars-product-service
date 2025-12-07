package com.ars.productservice.repository;

import com.ars.productservice.dto.response.shop.ShopDTO;
import com.dct.model.dto.request.BaseRequestDTO;
import org.springframework.data.domain.Page;

public interface ShopRepositoryCustom {
    Page<ShopDTO> getShopsWithPaging(BaseRequestDTO requestDTO);
}
