package com.ars.productservice.resource;

import com.ars.productservice.dto.request.shop.UpdateShopRequestDTO;
import com.ars.productservice.service.ShopService;
import com.dct.model.dto.request.BaseRequestDTO;
import com.dct.model.dto.response.BaseResponseDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ShopResource {
    private final ShopService shopService;

    public ShopResource(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping("/internal/shops/login-info/{userId}")
    public BaseResponseDTO getShopLoginInfo(@PathVariable @NotNull Integer userId) {
        return shopService.getShopLoginInfo(userId);
    }

    @GetMapping("/v1/shops")
    public BaseResponseDTO getShopsWithPaging(@ModelAttribute BaseRequestDTO requestDTO) {
        return shopService.getShopWithPaging(requestDTO);
    }

    @GetMapping("/v1/shops/{shopId}")
    public BaseResponseDTO getShopDetail(@PathVariable Integer shopId) {
        return shopService.getShopDetail(shopId);
    }

    @PutMapping("/v1/shops")
    public BaseResponseDTO updateShopInfo(@Valid @RequestBody UpdateShopRequestDTO requestDTO) {
        return shopService.updateShopInfo(requestDTO);
    }

    @PostMapping("/v1/shops/active/{shopId}")
    public BaseResponseDTO activateShop(@PathVariable Integer shopId) {
        return shopService.activateShop(shopId);
    }

    @PostMapping("/v1/shops/inactive/{shopId}")
    public BaseResponseDTO inactiveShop(@PathVariable Integer shopId) {
        return shopService.inactiveShop(shopId);
    }
}
