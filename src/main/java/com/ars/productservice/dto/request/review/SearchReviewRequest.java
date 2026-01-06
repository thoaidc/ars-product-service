package com.ars.productservice.dto.request.review;

import com.dct.model.dto.request.BaseRequestDTO;

public class SearchReviewRequest extends BaseRequestDTO {
    private Integer shopId;
    private Integer productId;
    private Integer customerId;

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}
