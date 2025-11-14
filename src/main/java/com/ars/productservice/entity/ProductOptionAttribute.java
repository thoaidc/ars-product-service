package com.ars.productservice.entity;

import com.dct.config.entity.AbstractAuditingEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "product_option_attribute")
@SuppressWarnings("unused")
public class ProductOptionAttribute extends AbstractAuditingEntity {

    @Column(name = "product_option_id", nullable = false)
    private Integer productOptionId;

    @Column
    private String image;

    @Column
    private String text;

    public Integer getProductOptionId() {
        return productOptionId;
    }

    public void setProductOptionId(Integer productOptionId) {
        this.productOptionId = productOptionId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

