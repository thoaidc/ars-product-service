package com.ars.productservice.entity;

import com.dct.config.entity.AbstractAuditingEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "voucher")
@SuppressWarnings("unused")
public class Voucher extends AbstractAuditingEntity {

    @Column(name = "shop_id", nullable = false)
    private Integer shopId;

    @Column(name = "code", nullable = false, unique = true, length = 20)
    private String code;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type", nullable = false)
    private VoucherType type;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    private VoucherStatus status;

    @Column(name = "date_started")
    private Integer dateStarted;

    @Column(name = "date_expired")
    private Integer dateExpired;

    @Column(name = "value", nullable = false, columnDefinition = "FLOAT DEFAULT 0.00")
    private Float value = 0.00f;

    public enum VoucherStatus {
        ACTIVE(1),
        INACTIVE(0);

        private final int value;

        VoucherStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum VoucherType {
        BY_VALUE(1),
        BY_PERCENTAGE(2);

        private final int value;

        VoucherType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public VoucherType getType() {
        return type;
    }

    public void setType(VoucherType type) {
        this.type = type;
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
}
