package com.ars.productservice.dto.request.voucher;

import com.ars.productservice.entity.Voucher;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

public class SaveVoucherRequestDTO {
    private Integer id;
    @NotNull
    private Integer shopId;
    @NotNull
    private Voucher.VoucherType type;
    @NotNull
    private Voucher.VoucherScope scope;
    @NotNull
    private Voucher.VoucherStatus status;
    @NotNull
    private String code;
    private Integer dateStarted;
    private Integer dateExpired;
    @NotNull
    private Float value = 0.00f;
    private Set<@NotNull Integer> productApplies = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Voucher.VoucherType getType() {
        return type;
    }

    public void setType(Voucher.VoucherType type) {
        this.type = type;
    }

    public Voucher.VoucherScope getScope() {
        return scope;
    }

    public void setScope(Voucher.VoucherScope scope) {
        this.scope = scope;
    }

    public Voucher.VoucherStatus getStatus() {
        return status;
    }

    public void setStatus(Voucher.VoucherStatus status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getDateStarted() {
        return dateStarted;
    }

    public void setDateStarted(Integer dateStarted) {
        this.dateStarted = dateStarted;
    }

    public Integer getDateExpired() {
        return dateExpired;
    }

    public void setDateExpired(Integer dateExpired) {
        this.dateExpired = dateExpired;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Set<Integer> getProductApplies() {
        return productApplies;
    }

    public void setProductApplies(Set<Integer> productApplies) {
        this.productApplies = productApplies;
    }
}
