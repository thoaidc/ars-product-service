package com.ars.productservice.entity;

import com.dct.config.entity.AbstractAuditingEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "shop_package_subscription")
@SuppressWarnings("unused")
public class ShopPackageSubscription extends AbstractAuditingEntity {
    @Column(name = "shop_id")
    private Integer shopId;

    @Column(name = "package_id")
    private Integer packageId;

    @Column(name = "package_code")
    private String packageCode;

    @Column(name = "package_name")
    private String packageName;

    @Column(name = "package_price")
    private BigDecimal packagePrice;

    @Column(name = "date_started")
    private Instant dateStarted;

    @Column(name = "date_expired")
    private Instant dateExpired;

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public BigDecimal getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(BigDecimal packagePrice) {
        this.packagePrice = packagePrice;
    }

    public Instant getDateStarted() {
        return dateStarted;
    }

    public void setDateStarted(Instant dateStarted) {
        this.dateStarted = dateStarted;
    }

    public Instant getDateExpired() {
        return dateExpired;
    }

    public void setDateExpired(Instant dateExpired) {
        this.dateExpired = dateExpired;
    }
}
