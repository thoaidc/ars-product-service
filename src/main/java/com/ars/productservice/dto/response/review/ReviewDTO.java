package com.ars.productservice.dto.response.review;

import com.dct.model.dto.response.AuditingDTO;

import java.time.Instant;

public class ReviewDTO extends AuditingDTO {
    private Integer shopId;
    private Integer productId;
    private Integer customerId;
    private String customerName;
    private String image;
    private String content;

    public ReviewDTO() {}

    public ReviewDTO(
        Integer id,
        Integer shopId,
        Integer productId,
        Integer customerId,
        String customerName,
        String image,
        String content,
        Instant createdDate
    ) {
        super(id, customerName, createdDate);
        this.shopId = shopId;
        this.productId = productId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.image = image;
        this.content = content;
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
