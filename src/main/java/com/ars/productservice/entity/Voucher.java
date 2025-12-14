package com.ars.productservice.entity;

import com.ars.productservice.dto.response.voucher.VoucherDTO;
import com.dct.config.entity.AbstractAuditingEntity;

import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.SqlResultSetMappings;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "voucher")
@SuppressWarnings("unused")
@SqlResultSetMappings(
    {
        @SqlResultSetMapping(
            name = "voucherGetWithPaging",
            classes = {
                @ConstructorResult(
                    targetClass = VoucherDTO.class,
                    columns = {
                        @ColumnResult(name = "id", type = Integer.class),
                        @ColumnResult(name = "shopId", type = Integer.class),
                        @ColumnResult(name = "type", type = Integer.class),
                        @ColumnResult(name = "status", type = Integer.class),
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "code", type = String.class),
                        @ColumnResult(name = "value", type = BigDecimal.class),
                        @ColumnResult(name = "dateStarted", type = Integer.class),
                        @ColumnResult(name = "dateExpired", type = Integer.class),
                        @ColumnResult(name = "createdDate", type = Instant.class),
                        @ColumnResult(name = "lastModifiedDate", type = Instant.class)
                    }
                )
            }
        )
    }
)
public class Voucher extends AbstractAuditingEntity {

    @Column(name = "shop_id", nullable = false)
    private Integer shopId;

    @Column(name = "name", nullable = false)
    private String name;

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

    @Column(name = "value", nullable = false)
    private BigDecimal value = BigDecimal.ZERO;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public VoucherStatus getStatus() {
        return status;
    }

    public void setStatus(VoucherStatus status) {
        this.status = status;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
