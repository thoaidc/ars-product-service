package com.ars.productservice.entity;

import com.ars.productservice.dto.response.attribute.AttributeResponseDTO;
import com.dct.config.entity.AbstractAuditingEntity;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "attribute")
@SqlResultSetMappings(
    {
        @SqlResultSetMapping(
            name = "attributeGetWithPaging",
            classes = {
                @ConstructorResult(
                    targetClass = AttributeResponseDTO.class,
                    columns = {
                        @ColumnResult(name = "id", type = Integer.class),
                        @ColumnResult(name = "shopId", type = Integer.class),
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "createdBy", type = String.class),
                        @ColumnResult(name = "createdDate", type = Instant.class)
                    }
                )
            }
        )
    }
)
@SuppressWarnings("unused")
public class Attribute extends AbstractAuditingEntity {

    @Column(name = "shop_id", nullable = false)
    private Integer shopId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

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
}
