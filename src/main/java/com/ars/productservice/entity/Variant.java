package com.ars.productservice.entity;

import com.dct.config.entity.AbstractAuditingEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
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

    @ManyToMany(
        cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH },
        fetch = FetchType.LAZY
    )
    @JoinTable(
        name = "variant_option",
        joinColumns = @JoinColumn(name = "variant_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "product_option_id", referencedColumnName = "id")
    )
    private List<ProductOption> productOptions = new ArrayList<>();

    @Transient
    @JsonIgnore
    private List<Integer> productOptionIds = new ArrayList<>();  // Not mapping to DB column, just to reference request mapping

    public List<Integer> getProductOptionIds() {
        return productOptionIds;
    }

    public void setProductOptionIds(List<Integer> productOptionIds) {
        this.productOptionIds = productOptionIds;
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

    public String getOriginalImage() {
        return originalImage;
    }

    public void setOriginalImage(String originalImage) {
        this.originalImage = originalImage;
    }

    public List<ProductOption> getProductOptions() {
        return productOptions;
    }

    public void setProductOptions(List<ProductOption> productOptions) {
        this.productOptions = productOptions;
    }
}
