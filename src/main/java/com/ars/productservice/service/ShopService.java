package com.ars.productservice.service;

import com.dct.model.dto.response.BaseResponseDTO;
import com.dct.model.event.UserCreatedEvent;

public interface ShopService {
    void registerShop(UserCreatedEvent userCreatedEvent);
    void rollbackRegisterShop(UserCreatedEvent userCreatedEvent, String errorMessage);
    BaseResponseDTO getShopLoginInfo(Integer userId);
}
