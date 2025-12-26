package com.ars.productservice.entity;

import com.ars.productservice.dto.response.product.ProductDTO;
import com.dct.config.entity.AbstractAuditingEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@SqlResultSetMappings(
    {
        @SqlResultSetMapping(
            name = "productGetWithPaging",
            classes = {
                @ConstructorResult(
                    targetClass = ProductDTO.class,
                    columns = {
                        @ColumnResult(name = "id", type = Integer.class),
                        @ColumnResult(name = "shopId", type = Integer.class),
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "code", type = String.class),
                        @ColumnResult(name = "price", type = BigDecimal.class),
                        @ColumnResult(name = "description", type = String.class),
                        @ColumnResult(name = "customizable", type = Boolean.class),
                        @ColumnResult(name = "status", type = String.class),
                        @ColumnResult(name = "thumbnailUrl", type = String.class),
                        @ColumnResult(name = "totalSales", type = Integer.class),
                        @ColumnResult(name = "createdBy", type = String.class),
                        @ColumnResult(name = "createdDate", type = Instant.class)
                    }
                ),
            }
        )
    }
)
@SuppressWarnings("unused")
public class Product extends AbstractAuditingEntity {

    @Column(name = "shop_id", nullable = false)
    private Integer shopId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 50, unique = true)
    private String code;

    @Column(nullable = false, precision = 21, scale = 6)
    private BigDecimal price;

    @Column(length = 1000)
    private String description;

    @Column(name = "is_customizable", nullable = false)
    private Boolean customizable;

    @Column(length = 20, nullable = false)
    private String status;

    @Column(name = "thumbnail_url", nullable = false)
    private String thumbnailUrl;

    @Column(name = "original_image")
    private String originalImage;

    @Column(name = "total_sales", nullable = false)
    private Integer totalSales;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<ProductImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<ProductOption> options = new ArrayList<>();

    @ManyToMany(
        cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH },
        fetch = FetchType.LAZY
    )
    @JoinTable(
        name = "product_product_group",
        joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "product_group_id", referencedColumnName = "id")
    )
    @JsonIgnore
    private List<ProductGroup> productGroups = new ArrayList<>();

    @ManyToMany(
        cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH },
        fetch = FetchType.LAZY
    )
    @JoinTable(
        name = "product_category",
        joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id")
    )
    @JsonIgnore
    private List<Category> categories = new ArrayList<>();

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

    public Integer getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(Integer totalSales) {
        this.totalSales = totalSales;
    }

    public List<ProductImage> getImages() {
        return images;
    }

    public void setImages(List<ProductImage> images) {
        this.images = images;
    }

    public List<ProductGroup> getProductGroups() {
        return productGroups;
    }

    public void setProductGroups(List<ProductGroup> productGroups) {
        this.productGroups = productGroups;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<ProductOption> getOptions() {
        return options;
    }

    public void setOptions(List<ProductOption> options) {
        this.options = options;
    }
}
