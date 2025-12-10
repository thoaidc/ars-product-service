package com.ars.productservice.dto.request.voucher;

import com.ars.productservice.entity.Voucher;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class SaveVoucherRequestDTO {
    private Integer id;
    @NotNull
    private Integer shopId;
    @NotNull
    private Voucher.VoucherType type;
    @NotNull
    private Voucher.VoucherStatus status;
    @NotNull
    private String code;
    private Integer dateStarted;
    private Integer dateExpired;
    @NotNull
    private BigDecimal value = BigDecimal.ZERO;

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

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
