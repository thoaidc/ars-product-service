package com.ars.productservice.dto.response.product;

import com.dct.model.dto.response.AuditingDTO;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class VariantDTO extends AuditingDTO {
    private Integer productId;
    private Integer attributeId;
    private String name;
    private BigDecimal price;
    private String thumbnailUrl;
    private List<ProductOptionDTO> productOptions = new ArrayList<>();

    public VariantDTO() {}

    public VariantDTO(
        Integer id,
        Integer productId,
        Integer attributeId,
        String name,
        BigDecimal price,
        String thumbnailUrl,
        String createdBy,
        Instant createdDate
    ) {
        super(id, createdBy, createdDate);
        this.productId = productId;
        this.attributeId = attributeId;
        this.name = name;
        this.price = price;
        this.thumbnailUrl = thumbnailUrl;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Integer attributeId) {
        this.attributeId = attributeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public List<ProductOptionDTO> getProductOptions() {
        return productOptions;
    }

    public void setProductOptions(List<ProductOptionDTO> productOptions) {
        this.productOptions = productOptions;
    }
}
