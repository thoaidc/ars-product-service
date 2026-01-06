package com.ars.productservice.entity;

import com.ars.productservice.dto.response.review.ReviewDTO;
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
@Table(name = "review")
@SqlResultSetMappings(
    {
        @SqlResultSetMapping(
            name = "reviewGetWithPaging",
            classes = {
                @ConstructorResult(
                    targetClass = ReviewDTO.class,
                    columns = {
                        @ColumnResult(name = "id", type = Integer.class),
                        @ColumnResult(name = "shopId", type = Integer.class),
                        @ColumnResult(name = "productId", type = Integer.class),
                        @ColumnResult(name = "customerId", type = Integer.class),
                        @ColumnResult(name = "customerName", type = String.class),
                        @ColumnResult(name = "image", type = String.class),
                        @ColumnResult(name = "content", type = String.class),
                        @ColumnResult(name = "createdDate", type = Instant.class)
                    }
                )
            }
        )
    }
)
public class Review extends AbstractAuditingEntity {
    @Column(name = "shop_id", nullable = false)
    private Integer shopId;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(name = "order_product_id", nullable = false)
    private Integer orderProductId;

    @Column(name = "customer_id", nullable = false)
    private Integer customerId;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "content", nullable = false)
    private String content;

    public Integer getOrderProductId() {
        return orderProductId;
    }

    public void setOrderProductId(Integer orderProductId) {
        this.orderProductId = orderProductId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
