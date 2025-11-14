package com.ars.productservice.entity;

import com.dct.config.entity.AbstractAuditingEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "variant_option")
@SuppressWarnings("unused")
public class VariantOption extends AbstractAuditingEntity {

    @Column(name = "variant_id", nullable = false)
    private Integer variantId;

    @Column(name = "product_option_id", nullable = false)
    private Integer productOptionId;

    public Integer getVariantId() {
        return variantId;
    }

    public void setVariantId(Integer variantId) {
        this.variantId = variantId;
    }

    public Integer getProductOptionId() {
        return productOptionId;
    }

    public void setProductOptionId(Integer productOptionId) {
        this.productOptionId = productOptionId;
    }
}
