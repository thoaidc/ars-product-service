package com.ars.productservice.entity;

import com.dct.config.entity.AbstractAuditingEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "product_product_group")
@SuppressWarnings("unused")
public class ProductProductGroup extends AbstractAuditingEntity {

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(name = "product_group_id", nullable = false)
    private Integer productGroupId;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductGroupId() {
        return productGroupId;
    }

    public void setProductGroupId(Integer productGroupId) {
        this.productGroupId = productGroupId;
    }
}
