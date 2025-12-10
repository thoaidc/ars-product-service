package com.ars.productservice.dto.response.voucher;

import com.dct.model.dto.response.AuditingDTO;

import java.util.HashSet;
import java.util.Set;

public class VoucherDTO extends AuditingDTO {
    private Integer shopId;
    private String code;
    private Integer type;
    private Integer scope;
    private Integer status;
    private Integer dateStarted;
    private Integer dateExpired;
    private Float value = 0.00f;
    private Set<Integer> productApplies = new HashSet<>();

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getScope() {
        return scope;
    }

    public void setScope(Integer scope) {
        this.scope = scope;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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
