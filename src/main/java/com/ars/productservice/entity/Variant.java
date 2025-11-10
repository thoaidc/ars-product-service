package com.ars.productservice.entity;

import com.dct.config.entity.AbstractAuditingEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "variant")
@SuppressWarnings("unused")
public class Variant extends AbstractAuditingEntity {

    @Column(length = 200, nullable = false)
    private String name;

    @Column(precision = 21, scale = 6)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "attribute_id", nullable = false)
    private Integer attributeId;

    @OneToMany(mappedBy = "variant", fetch = FetchType.LAZY)
    private List<VariantOption> variantOptions;

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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Integer attributeId) {
        this.attributeId = attributeId;
    }

    public List<VariantOption> getVariantOptions() {
        return variantOptions;
    }

    public void setVariantOptions(List<VariantOption> variantOptions) {
        this.variantOptions = variantOptions;
    }
}
