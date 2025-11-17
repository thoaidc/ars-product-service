package com.ars.productservice.dto.response.product;

import com.dct.model.dto.response.AuditingDTO;

public class ProductGroupDTO extends AuditingDTO {
    private Integer shopId;
    private String name;
    private String code;
    private String description;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
