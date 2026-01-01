package com.ars.productservice.dto.request.voucher;

import com.dct.model.dto.request.BaseRequestDTO;

import java.util.Set;

public class SearchVoucherRequest extends BaseRequestDTO {
    private Integer shopId;
    private Set<String> shopIds;
    private String code;
    private Integer type;
    private Integer active;
    private Integer dateStarted;
    private Integer dateExpired;

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Set<String> getShopIds() {
        return shopIds;
    }

    public void setShopIds(Set<String> shopIds) {
        this.shopIds = shopIds;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
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
}
