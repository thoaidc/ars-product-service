package com.ars.productservice.entity;

import com.ars.productservice.dto.response.voucher.VoucherDTO;
import com.dct.config.entity.AbstractAuditingEntity;

import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
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
        ),
        @SqlResultSetMapping(
            name = "voucherGetAllForUser",
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
                        @ColumnResult(name = "dateExpired", type = Integer.class)
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

    @Column(name = "type", nullable = false)
    private Integer type;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "date_started")
    private Integer dateStarted;

    @Column(name = "date_expired")
    private Integer dateExpired;

    @Column(name = "value", nullable = false)
    private BigDecimal value = BigDecimal.ZERO;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
