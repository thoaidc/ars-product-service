package com.ars.productservice.dto.response.product;

import com.ars.productservice.dto.response.attribute.AttributeDTO;
import com.dct.model.dto.response.AuditingDTO;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ProductDTO extends AuditingDTO {
    private Integer shopId;
    private String name;
    private String code;
    private BigDecimal price;
    private String description;
    private Boolean customizable;
    private String status;
    private String thumbnailUrl;
    private String originalImage;
    private List<VariantDTO> variants = new ArrayList<>();
    private List<Integer> categoryIds = new ArrayList<>();
    private List<Integer> groupIds = new ArrayList<>();
    private List<AttributeDTO> attributes = new ArrayList<>();

    public ProductDTO() {}

    public ProductDTO(
        Integer id,
        Integer shopId,
        String name,
        String code,
        BigDecimal price,
        String description,
        Boolean customizable,
        String status,
        String thumbnailUrl,
        String originalImage,
        String createdBy,
        Instant createdDate
    ) {
        super(id, createdBy, createdDate);
        this.shopId = shopId;
        this.name = name;
        this.code = code;
        this.price = price;
        this.description = description;
        this.customizable = customizable;
        this.status = status;
        this.thumbnailUrl = thumbnailUrl;
        this.originalImage = originalImage;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getCustomizable() {
        return customizable;
    }

    public void setCustomizable(Boolean customizable) {
        this.customizable = customizable;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getOriginalImage() {
        return originalImage;
    }

    public void setOriginalImage(String originalImage) {
        this.originalImage = originalImage;
    }

    public List<VariantDTO> getVariants() {
        return variants;
    }

    public void setVariants(List<VariantDTO> variants) {
        this.variants = variants;
    }

    public List<Integer> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Integer> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public List<Integer> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(List<Integer> groupIds) {
        this.groupIds = groupIds;
    }

    public List<AttributeDTO> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeDTO> attributes) {
        this.attributes = attributes;
    }
}
