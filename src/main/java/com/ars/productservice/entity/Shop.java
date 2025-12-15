package com.ars.productservice.entity;

import com.ars.productservice.dto.response.shop.ShopDTO;
import com.dct.config.entity.AbstractAuditingEntity;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.SqlResultSetMappings;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "shop")
@SuppressWarnings("unused")
@SqlResultSetMappings(
    {
        @SqlResultSetMapping(
            name = "shopGetWithPaging",
            classes = {
                @ConstructorResult(
                    targetClass = ShopDTO.class,
                    columns = {
                        @ColumnResult(name = "id", type = Integer.class),
                        @ColumnResult(name = "ownerId", type = Integer.class),
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "status", type = String.class),
                        @ColumnResult(name = "createdDate", type = Instant.class)
                    }
                )
            }
        )
    }
)
public class Shop extends AbstractAuditingEntity {
    @Column(name = "owner_id")
    private Integer ownerId;
    private String name;
    private String description;
    private String email;
    private String phone;
    private String status;
    @Column(name = "total_sales")
    private Integer totalSales;

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(Integer totalSales) {
        this.totalSales = totalSales;
    }
}
