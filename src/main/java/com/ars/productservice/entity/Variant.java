package com.ars.productservice.entity;

import com.dct.config.entity.AbstractAuditingEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "variant")
@SuppressWarnings("unused")
public class Variant extends AbstractAuditingEntity {

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(name = "attribute_id", nullable = false)
    private Integer attributeId;

    @Column(length = 200, nullable = false)
    private String name;

    @Column(precision = 21, scale = 6)
    private BigDecimal price;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "original_image")
    private String originalImage;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id", referencedColumnName = "id")
    private List<VariantOption> variantOptions;

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

    public String getOriginalImage() {
        return originalImage;
    }

    public void setOriginalImage(String originalImage) {
        this.originalImage = originalImage;
    }

    public List<VariantOption> getVariantOptions() {
        return variantOptions;
    }

    public void setVariantOptions(List<VariantOption> variantOptions) {
        this.variantOptions = variantOptions;
    }
}
