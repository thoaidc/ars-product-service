package com.ars.productservice.dto.request.attribute;

import com.dct.model.dto.request.BaseRequestDTO;
import jakarta.validation.constraints.NotNull;

public class SearchAttributeRequest extends BaseRequestDTO {
    @NotNull
    private Integer shopId;

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }
}
