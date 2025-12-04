package com.ars.productservice.service.impl;

import com.ars.productservice.constants.ExceptionConstants;
import com.ars.productservice.constants.ShopConstants;
import com.ars.productservice.dto.mapping.ShopInfoLogin;
import com.ars.productservice.entity.OutBox;
import com.ars.productservice.entity.Shop;
import com.ars.productservice.repository.OutBoxRepository;
import com.ars.productservice.repository.ShopRepository;
import com.ars.productservice.service.ShopService;

import com.dct.model.common.BaseCommon;
import com.dct.model.common.JsonUtils;
import com.dct.model.constants.BaseOutBoxConstants;
import com.dct.model.dto.response.BaseResponseDTO;
import com.dct.model.event.UserCreatedEvent;
import com.dct.model.event.UserShopCompletionEvent;

import com.dct.model.event.UserShopFailureEvent;
import com.dct.model.exception.BaseBadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ShopServiceImpl implements ShopService {
    private static final String ENTITY_NAME = "com.ars.productservice.service.impl.ShopServiceImpl";
    private final ShopRepository shopRepository;
    private final OutBoxRepository outBoxRepository;

    public ShopServiceImpl(ShopRepository shopRepository, OutBoxRepository outBoxRepository) {
        this.shopRepository = shopRepository;
        this.outBoxRepository = outBoxRepository;
    }

    @Override
    @Transactional
    public void registerShop(UserCreatedEvent userCreatedEvent) {
        if (shopRepository.existsByUserIdOrEmail(userCreatedEvent.getUserId(), userCreatedEvent.getEmail())) {
            throw new BaseBadRequestException(ENTITY_NAME, ExceptionConstants.SHOP_EXISTED);
        }

        Shop shop = new Shop();
        shop.setUserId(userCreatedEvent.getUserId());
        shop.setName(userCreatedEvent.getShopName());
        shop.setSlug(BaseCommon.normalizeName(shop.getName()));
        shop.setEmail(userCreatedEvent.getEmail());
        shop.setPhone(userCreatedEvent.getPhone());
        shop.setStatus(ShopConstants.Status.ACTIVE);
        shopRepository.save(shop);
        UserShopCompletionEvent userShopCompletionEvent = UserShopCompletionEvent.builder()
                .userId(userCreatedEvent.getUserId())
                .shopId(shop.getId())
                .shopName(shop.getName())
                .sagaId(userCreatedEvent.getSagaId())
                .build();
        OutBox outBox = new OutBox();
        outBox.setSagaId(userCreatedEvent.getSagaId());
        outBox.setStatus(BaseOutBoxConstants.Status.PENDING);
        outBox.setType(BaseOutBoxConstants.Type.USER_REGISTER_SHOP_COMPLETION);
        outBox.setValue(JsonUtils.toJsonString(userShopCompletionEvent));
        outBoxRepository.save(outBox);
    }

    @Override
    @Transactional
    public void rollbackRegisterShop(UserCreatedEvent userCreatedEvent, String errorMessage) {
        UserShopFailureEvent userShopFailureEvent = UserShopFailureEvent.builder()
                .userId(userCreatedEvent.getUserId())
                .sagaId(userCreatedEvent.getSagaId())
                .errorCode(BaseOutBoxConstants.Type.USER_REGISTER_SHOP_FAILURE)
                .errorMessage(errorMessage)
                .build();
        OutBox outBox = new OutBox();
        outBox.setSagaId(userCreatedEvent.getSagaId());
        outBox.setStatus(BaseOutBoxConstants.Status.PENDING);
        outBox.setType(BaseOutBoxConstants.Type.USER_REGISTER_SHOP_FAILURE);
        outBox.setValue(JsonUtils.toJsonString(userShopFailureEvent));
        outBoxRepository.save(outBox);
    }

    @Override
    public BaseResponseDTO getShopLoginInfo(Integer userId) {
        Optional<ShopInfoLogin> shopInfoLoginOptional = shopRepository.findShopInfoLoginByUserId(userId);

        if (shopInfoLoginOptional.isEmpty()) {
            throw new BaseBadRequestException(ENTITY_NAME, ExceptionConstants.SHOP_EXISTED);
        }

        return BaseResponseDTO.builder().ok(shopInfoLoginOptional.get());
    }
}
