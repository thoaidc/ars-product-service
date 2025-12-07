package com.ars.productservice.service.impl;

import com.ars.productservice.constants.ExceptionConstants;
import com.ars.productservice.constants.ProductConstants;
import com.ars.productservice.constants.ShopConstants;
import com.ars.productservice.dto.mapping.ShopInfoLogin;
import com.ars.productservice.dto.request.shop.UpdateShopRequestDTO;
import com.ars.productservice.entity.OutBox;
import com.ars.productservice.entity.Shop;
import com.ars.productservice.repository.OutBoxRepository;
import com.ars.productservice.repository.ProductRepository;
import com.ars.productservice.repository.ShopRepository;
import com.ars.productservice.service.ShopService;

import com.dct.config.common.FileUtils;
import com.dct.model.common.BaseCommon;
import com.dct.model.common.JsonUtils;
import com.dct.model.constants.BaseOutBoxConstants;
import com.dct.model.dto.request.BaseRequestDTO;
import com.dct.model.dto.response.BaseResponseDTO;
import com.dct.model.event.UserCreatedEvent;
import com.dct.model.event.UserShopCompletionEvent;
import com.dct.model.event.UserShopFailureEvent;
import com.dct.model.exception.BaseBadRequestException;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Optional;

@Service
public class ShopServiceImpl implements ShopService {
    private static final String ENTITY_NAME = "com.ars.productservice.service.impl.ShopServiceImpl";
    private final ShopRepository shopRepository;
    private final OutBoxRepository outBoxRepository;
    private final ProductRepository productRepository;
    private final FileUtils fileUtils = new FileUtils();

    public ShopServiceImpl(ShopRepository shopRepository,
                           OutBoxRepository outBoxRepository,
                           ProductRepository productRepository) {
        this.shopRepository = shopRepository;
        this.outBoxRepository = outBoxRepository;
        this.productRepository = productRepository;
        this.fileUtils.setPrefixPath(ProductConstants.Upload.PREFIX);
        this.fileUtils.setUploadDirectory(ProductConstants.Upload.LOCATION);
    }

    @Override
    @Transactional
    public void registerShop(UserCreatedEvent userCreatedEvent) {
        if (Objects.isNull(userCreatedEvent.getUserId()) || Objects.isNull(userCreatedEvent.getShopName())) {
            throw new BaseBadRequestException(ENTITY_NAME, "Missing owner info or shop name");
        }

        if (shopRepository.existsByUserIdAndName(userCreatedEvent.getUserId(), userCreatedEvent.getShopName())) {
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

    @Override
    public BaseResponseDTO getShopWithPaging(BaseRequestDTO requestDTO) {
        return null;
    }

    @Override
    public BaseResponseDTO getShopDetail(Integer shopId) {
        return null;
    }

    @Override
    @Transactional
    public BaseResponseDTO updateShopInfo(UpdateShopRequestDTO requestDTO) {
        Optional<Shop> shopOptional = shopRepository.findById(requestDTO.getId());

        if (shopOptional.isEmpty()) {
            throw new BaseBadRequestException(ENTITY_NAME, ExceptionConstants.SHOP_NOT_EXISTED);
        }

        Shop shop = shopOptional.get();
        BeanUtils.copyProperties(requestDTO, shop, "id", "banner", "logo");
        String newLogoUrl = fileUtils.autoCompressImageAndSave(requestDTO.getLogo());
        String newBannerUrl = fileUtils.autoCompressImageAndSave(requestDTO.getBanner());

        if (StringUtils.hasText(newLogoUrl)) {
            fileUtils.delete(shop.getLogo());
            shop.setLogo(newLogoUrl);
        }

        if (StringUtils.hasText(newBannerUrl)) {
            fileUtils.delete(shop.getBanner());
            shop.setBanner(newBannerUrl);
        }

        return BaseResponseDTO.builder().ok(shopRepository.save(shop));
    }

    @Override
    @Transactional
    public BaseResponseDTO activateShop(Integer shopId) {
        shopRepository.updateShopStatusById(shopId, ShopConstants.Status.ACTIVE);
        productRepository.updateStatusByShopId(shopId, ProductConstants.Status.ACTIVE);
        return BaseResponseDTO.builder().ok();
    }

    @Override
    @Transactional
    public BaseResponseDTO inactiveShop(Integer shopId) {
        shopRepository.updateShopStatusById(shopId, ShopConstants.Status.INACTIVE);
        productRepository.updateStatusByShopId(shopId, ProductConstants.Status.INACTIVE);
        return BaseResponseDTO.builder().ok();
    }
}
