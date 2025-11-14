package com.ars.productservice.entity;

import com.dct.config.entity.AbstractAuditingEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "product_category")
@SuppressWarnings("unused")
public class ProductCategory extends AbstractAuditingEntity {

    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
