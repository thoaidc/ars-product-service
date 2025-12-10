package com.ars.productservice.dto.request.voucher;

import com.dct.model.dto.request.BaseRequestDTO;

public class SearchVoucherRequest extends BaseRequestDTO {
    private Integer shopId;

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }
}
