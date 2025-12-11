package com.ars.productservice.dto.response.product;

import com.ars.productservice.dto.mapping.ProductCheckOrderInfo;
import com.ars.productservice.dto.mapping.VoucherCheckOrderInfo;

import java.util.ArrayList;
import java.util.List;

public class CheckOrderInfoResponseDTO {
    List<ProductCheckOrderInfo> products = new ArrayList<>();
    List<VoucherCheckOrderInfo> vouchers = new ArrayList<>();

    public List<ProductCheckOrderInfo> getProducts() {
        return products;
    }

    public void setProducts(List<ProductCheckOrderInfo> products) {
        this.products = products;
    }

    public List<VoucherCheckOrderInfo> getVouchers() {
        return vouchers;
    }

    public void setVouchers(List<VoucherCheckOrderInfo> vouchers) {
        this.vouchers = vouchers;
    }
}
