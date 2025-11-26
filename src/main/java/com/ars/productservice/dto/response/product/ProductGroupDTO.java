package com.ars.productservice.dto.response.product;

import com.dct.model.dto.response.AuditingDTO;

public class ProductGroupDTO extends AuditingDTO {
    private Integer shopId;
    private String name;

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
