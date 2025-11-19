package com.ars.productservice.entity;

import com.ars.productservice.dto.response.product.ProductOptionDTO;
import com.dct.config.entity.AbstractAuditingEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product_option")
@SqlResultSetMappings(
    {
        @SqlResultSetMapping(
            name = "productOptionGetWithPaging",
            classes = {
                @ConstructorResult(
                    targetClass = ProductOptionDTO.class,
                    columns = {
                        @ColumnResult(name = "id", type = Integer.class),
                        @ColumnResult(name = "productId", type = Integer.class),
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "type", type = String.class),
                        @ColumnResult(name = "topPercentage", type = Float.class),
                        @ColumnResult(name = "leftPercentage", type = Float.class),
                        @ColumnResult(name = "widthPercentage", type = Float.class),
                        @ColumnResult(name = "heightPercentage", type = Float.class),
                        @ColumnResult(name = "description", type = String.class),
                        @ColumnResult(name = "createdBy", type = String.class),
                        @ColumnResult(name = "createdDate", type = Instant.class)
                    }
                )
            }
        )
    }
)
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

    @OneToMany(mappedBy = "productOption", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<ProductOptionAttribute> attributes = new ArrayList<>();

    @Transient
    @JsonIgnore
    private Integer refId; // Not mapping to DB column, just to reference request mapping

    public Integer getRefId() {
        return refId;
    }

    public void setRefId(Integer refId) {
        this.refId = refId;
    }

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

    public List<ProductOptionAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ProductOptionAttribute> attributes) {
        this.attributes = attributes;
    }
}
