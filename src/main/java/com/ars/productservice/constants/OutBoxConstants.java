package com.ars.productservice.constants;

public interface OutBoxConstants {
    interface Type {
        String REGISTER_USER_WITH_SHOP_COMPLETION = "REGISTER_USER_WITH_SHOP_COMPLETION";
        String REGISTER_USER_WITH_SHOP_FAILURE = "REGISTER_USER_WITH_SHOP_FAILURE";
    }

    interface Status {
        String PENDING = "PENDING";
        String COMPLETION = "COMPLETION";
    }

    int DELAY_TIME = 5000; // 5 seconds
}
