package com.ars.productservice.dto.request.review;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

public class CheckCustomerReviewRequest {
    @NotNull
    private Integer customerId;
    @NotEmpty
    private Set<@NotNull Integer> productIds = new HashSet<>();

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Set<Integer> getProductIds() {
        return productIds;
    }

    public void setProductIds(Set<Integer> productIds) {
        this.productIds = productIds;
    }
}
