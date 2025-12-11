package com.ars.productservice.dto.request.product;

import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

public class CheckOrderInfoRequestDTO {
    private Set<@NotNull Integer> productIds = new HashSet<>();
    private Set<@NotNull Integer> voucherIds = new HashSet<>();

    public Set<Integer> getProductIds() {
        return productIds;
    }

    public void setProductIds(Set<Integer> productIds) {
        this.productIds = productIds;
    }

    public Set<Integer> getVoucherIds() {
        return voucherIds;
    }

    public void setVoucherIds(Set<Integer> voucherIds) {
        this.voucherIds = voucherIds;
    }
}
