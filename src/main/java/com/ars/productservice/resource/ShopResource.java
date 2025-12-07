package com.ars.productservice.resource;

import com.ars.productservice.service.ShopService;
import com.dct.model.dto.response.BaseResponseDTO;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
}
