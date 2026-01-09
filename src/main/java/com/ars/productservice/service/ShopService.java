package com.ars.productservice.service;

import com.ars.productservice.dto.UserIDRequest;
import com.ars.productservice.dto.request.shop.UpdateShopRequestDTO;
import com.dct.model.dto.request.BaseRequestDTO;
import com.dct.model.dto.response.BaseResponseDTO;
import com.dct.model.event.UserCreatedEvent;

public interface ShopService {
    void registerShop(UserCreatedEvent userCreatedEvent);
    void rollbackRegisterShop(UserCreatedEvent userCreatedEvent, String errorMessage);
    BaseResponseDTO getShopLoginInfo(Integer userId);
    BaseResponseDTO getShopWithPaging(BaseRequestDTO requestDTO);
    BaseResponseDTO getShopDetail(Integer shopId);
    BaseResponseDTO updateShopInfo(UpdateShopRequestDTO requestDTO);
    BaseResponseDTO activateShop(Integer shopId);
    BaseResponseDTO inactiveShop(Integer shopId);
    BaseResponseDTO getUserByIds(UserIDRequest request);
}
