package com.ars.productservice.dto.response.voucher;

import com.dct.model.dto.response.AuditingDTO;

import java.math.BigDecimal;
import java.time.Instant;

public class VoucherDTO extends AuditingDTO {
    private Integer shopId;
    private String name;
    private String code;
    private Integer type;
    private Integer status;
    private Integer dateStarted;
    private Integer dateExpired;
    private BigDecimal value;

    public VoucherDTO() {}

    public VoucherDTO(
        Integer id,
        Integer shopId,
        Integer type,
        Integer status,
        String name,
        String code,
        BigDecimal value,
        Integer dateStarted,
        Integer dateExpired,
        Instant createdDate,
        Instant lastModifiedDate
    ) {
        super.setId(id);
        super.setCreatedDate(createdDate);
        super.setLastModifiedDate(lastModifiedDate);
        this.shopId = shopId;
        this.type = type;
        this.status = status;
        this.name = name;
        this.code = code;
        this.value = value;
        this.dateStarted = dateStarted;
        this.dateExpired = dateExpired;
    }

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
