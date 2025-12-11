package com.ars.productservice.dto.mapping;

import java.math.BigDecimal;

public interface VoucherCheckOrderInfo {
    Integer getId();
    Integer getShopId();
    String getCode();
    Integer getType();
    Integer getStatus();
    Integer getDateStarted();
    Integer getDateExpired();
    BigDecimal getValue();
}
