package com.ars.productservice.entity;

import com.dct.config.entity.AbstractAuditingEntity;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "product_option")
@SuppressWarnings("unused")
public class ProductOption extends AbstractAuditingEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String type;

    @Column
    private String description;

    @Column(columnDefinition = "json")
    private String data;

    @OneToMany(mappedBy = "productOption", fetch = FetchType.LAZY)
    private List<ProductOptionAttribute> attributes;

    @OneToMany(mappedBy = "productOption", fetch = FetchType.LAZY)
    private List<VariantOption> variantOptions;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<ProductOptionAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ProductOptionAttribute> attributes) {
        this.attributes = attributes;
    }

    public List<VariantOption> getVariantOptions() {
        return variantOptions;
    }

    public void setVariantOptions(List<VariantOption> variantOptions) {
        this.variantOptions = variantOptions;
    }
}
