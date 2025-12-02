package com.ars.productservice.service;

import com.dct.model.event.UserCreatedEvent;

public interface ShopService {
    void registerShop(UserCreatedEvent userCreatedEvent);
    void rollbackRegisterShop(UserCreatedEvent userCreatedEvent, String errorMessage);
}
