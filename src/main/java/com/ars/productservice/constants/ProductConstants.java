package com.ars.productservice.constants;

public interface ProductConstants {
    interface Status {
        String ACTIVE = "ACTIVE";
        String INACTIVE = "INACTIVE";
        String DELETED = "DELETED";
    }

    interface Upload {
        String prefix = "/uploads/products/";
        String location = "opt/uploads/products/";
    }
}
