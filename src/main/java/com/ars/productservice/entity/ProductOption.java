package com.ars.productservice.entity;

import com.dct.config.entity.AbstractAuditingEntity;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "product_option")
@SuppressWarnings("unused")
public class ProductOption extends AbstractAuditingEntity {

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String type;

    @Column(name = "top_percentage")
    private Float topPercentage;

    @Column(name = "left_percentage")
    private Float leftPercentage;

    @Column(name = "width_percentage")
    private Float widthPercentage;

    @Column(name = "height_percentage")
    private Float heightPercentage;

    @Column
    private String description;

    @Column(length = 2000)
    private String data;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_option_id", referencedColumnName = "id")
    private List<ProductOptionAttribute> attributes;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
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

    public Float getTopPercentage() {
        return topPercentage;
    }

    public void setTopPercentage(Float topPercentage) {
        this.topPercentage = topPercentage;
    }

    public Float getLeftPercentage() {
        return leftPercentage;
    }

    public void setLeftPercentage(Float leftPercentage) {
        this.leftPercentage = leftPercentage;
    }

    public Float getWidthPercentage() {
        return widthPercentage;
    }

    public void setWidthPercentage(Float widthPercentage) {
        this.widthPercentage = widthPercentage;
    }

    public Float getHeightPercentage() {
        return heightPercentage;
    }

    public void setHeightPercentage(Float heightPercentage) {
        this.heightPercentage = heightPercentage;
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
}
