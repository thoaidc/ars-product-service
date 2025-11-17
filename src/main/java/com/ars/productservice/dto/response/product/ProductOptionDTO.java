package com.ars.productservice.dto.response.product;

import com.ars.productservice.entity.ProductOptionAttribute;
import com.dct.model.dto.response.AuditingDTO;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ProductOptionDTO extends AuditingDTO {
    private Integer productId;
    private String name;
    private String type;
    private Float topPercentage;
    private Float leftPercentage;
    private Float widthPercentage;
    private Float heightPercentage;
    private String description;
    private List<ProductOptionAttribute> attributes = new ArrayList<>();

    public ProductOptionDTO() {}

    public ProductOptionDTO(
        Integer id,
        Integer productId,
        String name,
        String type,
        Float topPercentage,
        Float leftPercentage,
        Float widthPercentage,
        Float heightPercentage,
        String description,
        String createdBy,
        Instant createdDate
    ) {
        super(id, createdBy, createdDate);
        this.productId = productId;
        this.name = name;
        this.type = type;
        this.topPercentage = topPercentage;
        this.leftPercentage = leftPercentage;
        this.widthPercentage = widthPercentage;
        this.heightPercentage = heightPercentage;
        this.description = description;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getTopPercentage() {
        return topPercentage;
    }

    public void setTopPercentage(Float topPercentage) {
        this.topPercentage = topPercentage;
    }

    public Float getLeftPercentage() {
        return leftPercentage;
    }

    public void setLeftPercentage(Float leftPercentage) {
        this.leftPercentage = leftPercentage;
    }

    public Float getWidthPercentage() {
        return widthPercentage;
    }

    public void setWidthPercentage(Float widthPercentage) {
        this.widthPercentage = widthPercentage;
    }

    public Float getHeightPercentage() {
        return heightPercentage;
    }

    public void setHeightPercentage(Float heightPercentage) {
        this.heightPercentage = heightPercentage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ProductOptionAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ProductOptionAttribute> attributes) {
        this.attributes = attributes;
    }
}
