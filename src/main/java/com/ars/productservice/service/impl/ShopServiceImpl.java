package com.ars.productservice.service.impl;

import com.ars.productservice.constants.ExceptionConstants;
import com.ars.productservice.constants.ProductConstants;
import com.ars.productservice.constants.ShopConstants;
import com.ars.productservice.dto.UserIDRequest;
import com.ars.productservice.dto.mapping.ShopInfoLogin;
import com.ars.productservice.dto.request.shop.UpdateShopRequestDTO;
import com.ars.productservice.dto.response.shop.ShopDTO;
import com.ars.productservice.dto.response.shop.ShopOwnerInfo;
import com.ars.productservice.entity.OutBox;
import com.ars.productservice.entity.Shop;
import com.ars.productservice.repository.OutBoxRepository;
import com.ars.productservice.repository.ProductRepository;
import com.ars.productservice.repository.ShopRepository;
import com.ars.productservice.service.ShopService;

import com.dct.config.common.HttpClientUtils;
import com.dct.model.common.JsonUtils;
import com.dct.model.constants.BaseOutBoxConstants;
import com.dct.model.dto.request.BaseRequestDTO;
import com.dct.model.dto.response.BaseResponseDTO;
import com.dct.model.event.UserCreatedEvent;
import com.dct.model.event.UserShopCompletionEvent;
import com.dct.model.event.UserShopFailureEvent;
import com.dct.model.exception.BaseBadRequestException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShopServiceImpl implements ShopService {
    private static final String ENTITY_NAME = "com.ars.productservice.service.impl.ShopServiceImpl";
    private final ShopRepository shopRepository;
    private final OutBoxRepository outBoxRepository;
    private final ProductRepository productRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public ShopServiceImpl(ShopRepository shopRepository,
                           OutBoxRepository outBoxRepository,
                           ProductRepository productRepository,
                           RestTemplate restTemplate,
                           ObjectMapper objectMapper) {
        this.shopRepository = shopRepository;
        this.outBoxRepository = outBoxRepository;
        this.productRepository = productRepository;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional
    public void registerShop(UserCreatedEvent userCreatedEvent) {
        if (Objects.isNull(userCreatedEvent.getUserId()) || Objects.isNull(userCreatedEvent.getShopName())) {
            throw new BaseBadRequestException(ENTITY_NAME, "Missing owner info or shop name");
        }

        if (shopRepository.existsByOwnerIdAndName(userCreatedEvent.getUserId(), userCreatedEvent.getShopName())) {
            throw new BaseBadRequestException(ENTITY_NAME, ExceptionConstants.SHOP_EXISTED);
        }

        Shop shop = new Shop();
        shop.setOwnerId(userCreatedEvent.getUserId());
        shop.setName(userCreatedEvent.getShopName());
        shop.setEmail(userCreatedEvent.getEmail());
        shop.setPhone(userCreatedEvent.getPhone());
        shop.setStatus(ShopConstants.Status.ACTIVE);
        shop.setTotalSales(0);
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
        Optional<ShopInfoLogin> shopInfoLoginOptional = shopRepository.findShopInfoLoginByOwnerId(userId);

        if (shopInfoLoginOptional.isEmpty()) {
            throw new BaseBadRequestException(ENTITY_NAME, ExceptionConstants.SHOP_EXISTED);
        }

        return BaseResponseDTO.builder().ok(shopInfoLoginOptional.get());
    }

    @Override
    public BaseResponseDTO getShopWithPaging(BaseRequestDTO requestDTO) {
        Page<ShopDTO> shopPage = shopRepository.getShopsWithPaging(requestDTO);
        List<ShopDTO> shops = shopPage.getContent();
        long totalShops = shopPage.getTotalElements();

        if (totalShops > 0 && !shops.isEmpty()) {
            List<String> ownerIds = shops.stream().map(shop -> String.valueOf(shop.getOwnerId())).toList();
            Map<String, List<String>> params = Map.of("ownerIds", ownerIds);
            BaseResponseDTO responseDTO = HttpClientUtils.builder()
                    .restTemplate(restTemplate)
                    .url("http://localhost:8000/api/internal/users/owners-info")
                    .method(HttpMethod.GET)
                    .params(params)
                    .execute(BaseResponseDTO.class);
            if (Objects.nonNull(responseDTO) && Objects.nonNull(responseDTO.getResult())) {
                TypeReference<List<ShopOwnerInfo>> typeReference = new TypeReference<>() {};
                List<ShopOwnerInfo> shopOwnerInfos = objectMapper.convertValue(responseDTO.getResult(), typeReference);
                Map<Integer, ShopOwnerInfo> shopOwnerInfoMap = shopOwnerInfos.stream().collect(
                    Collectors.toMap(ShopOwnerInfo::getOwnerId, shopOwnerInfo -> shopOwnerInfo)
                );

                for (ShopDTO shop : shops) {
                    ShopOwnerInfo shopOwnerInfo = shopOwnerInfoMap.get(shop.getOwnerId());

                    if (Objects.nonNull(shopOwnerInfo)) {
                        shop.setOwnerName(shopOwnerInfo.getOwnerName());
                        shop.setOwnerEmail(shopOwnerInfo.getOwnerEmail());
                        shop.setOwnerPhone(shopOwnerInfo.getOwnerPhone());
                    }
                }
            }
        }

        return BaseResponseDTO.builder().total(totalShops).ok(shops);
    }

    @Override
    public BaseResponseDTO getShopDetail(Integer shopId) {
        Optional<Shop> shopOptional = shopRepository.findById(shopId);

        if (shopOptional.isEmpty()) {
            throw new BaseBadRequestException(ENTITY_NAME, ExceptionConstants.SHOP_NOT_EXISTED);
        }

        Shop shop = shopOptional.get();
        ShopDTO shopDTO = new ShopDTO();
        BeanUtils.copyProperties(shop, shopDTO);
        BaseResponseDTO responseDTO = HttpClientUtils.builder()
                .restTemplate(restTemplate)
                .url("http://localhost:8000/api/internal/users/owners-info/" + shop.getOwnerId())
                .method(HttpMethod.GET)
                .execute(BaseResponseDTO.class);

        if (Objects.nonNull(responseDTO) && Objects.nonNull(responseDTO.getResult())) {
            ShopOwnerInfo shopOwnerInfo = JsonUtils.convertValue(responseDTO.getResult(), ShopOwnerInfo.class);
            shopDTO.setOwnerName(shopOwnerInfo.getOwnerName());
            shopDTO.setOwnerEmail(shopOwnerInfo.getOwnerEmail());
            shopDTO.setOwnerPhone(shopOwnerInfo.getOwnerPhone());
        }

        return BaseResponseDTO.builder().ok(shopDTO);
    }

    @Override
    @Transactional
    public BaseResponseDTO updateShopInfo(UpdateShopRequestDTO requestDTO) {
        Optional<Shop> shopOptional = shopRepository.findById(requestDTO.getId());

        if (shopOptional.isEmpty()) {
            throw new BaseBadRequestException(ENTITY_NAME, ExceptionConstants.SHOP_NOT_EXISTED);
        }

        Shop shop = shopOptional.get();
        BeanUtils.copyProperties(requestDTO, shop, "id");
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

    @Override
    public BaseResponseDTO getUserByIds(UserIDRequest request) {
        return BaseResponseDTO.builder().ok(shopRepository.findUserDTOByIds(request.getIds()));
    }
}
