package com.ars.productservice.dto.response.attribute;

import com.dct.model.dto.response.AuditingDTO;

import java.time.Instant;

public class AttributeResponseDTO extends AuditingDTO {
    private Integer shopId;
    private String name;

    public AttributeResponseDTO() {}

    public AttributeResponseDTO(Integer id, Integer shopId, String name, String createdBy, Instant createdDate) {
        super(id, createdBy, createdDate);
        this.shopId = shopId;
        this.name = name;
    }

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
