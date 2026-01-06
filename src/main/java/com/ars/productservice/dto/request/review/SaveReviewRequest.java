package com.ars.productservice.dto.request.review;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class SaveReviewRequest {

    @NotNull
    private Integer customerId;
    @NotBlank
    private String customerName;
    @NotEmpty
    private List<@Valid Review> reviews = new ArrayList<>();

    public static class Review {
        @NotNull
        private Integer shopId;
        @NotNull
        private Integer productId;
        @NotNull
        private Integer orderProductId;
        @NotBlank
        private String content;
        private MultipartFile image;

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

        public Integer getOrderProductId() {
            return orderProductId;
        }

        public void setOrderProductId(Integer orderProductId) {
            this.orderProductId = orderProductId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public MultipartFile getImage() {
            return image;
        }

        public void setImage(MultipartFile image) {
            this.image = image;
        }
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

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
