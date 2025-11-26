package com.ars.productservice.dto.response.product;

import com.ars.productservice.entity.ProductOptionAttribute;
import com.dct.model.dto.response.AuditingDTO;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ProductOptionDTO extends AuditingDTO {
    private Integer productId;
    private String name;
    private List<ProductOptionAttribute> attributes = new ArrayList<>();

    public ProductOptionDTO() {}

    public ProductOptionDTO(
        Integer id,
        Integer productId,
        String name,
        String createdBy,
        Instant createdDate
    ) {
        super(id, createdBy, createdDate);
        this.productId = productId;
        this.name = name;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductOptionAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ProductOptionAttribute> attributes) {
        this.attributes = attributes;
    }
}
