package com.ars.productservice.dto.request.product;

import com.dct.model.dto.request.BaseRequestDTO;

public class SearchProductGroupRequest extends BaseRequestDTO {
    private Integer shopId;

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }
}
