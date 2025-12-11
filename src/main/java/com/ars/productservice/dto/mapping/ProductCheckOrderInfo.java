package com.ars.productservice.dto.mapping;

import java.math.BigDecimal;

public interface ProductCheckOrderInfo {
    Integer getId();
    Integer getShopId();
    String getName();
    String getCode();
    BigDecimal getPrice();
    String getStatus();
    String getThumbnailUrl();
}
