package com.ars.productservice.dto.mapping;

public interface ProductOptionMapping {
    Integer getId();
    Integer getProductId();
    String getName();
    String getType();
    Float getTopPercentage();
    Float getLeftPercentage();
    Float getWidthPercentage();
    Float getHeightPercentage();
    String getDescription();
}
