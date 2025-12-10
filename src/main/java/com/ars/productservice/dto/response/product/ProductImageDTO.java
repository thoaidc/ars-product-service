package com.ars.productservice.dto.response.product;

import com.dct.model.dto.response.AuditingDTO;

public class ProductImageDTO extends AuditingDTO {
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
