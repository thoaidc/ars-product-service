package com.ars.productservice.queue.listener;

import com.ars.productservice.service.ProductService;
import com.dct.model.common.JsonUtils;
import com.dct.model.constants.BaseKafkaConstants;
import com.dct.model.event.UpdateProductSaleQuantityEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UpdateProductSaleQuantityListener {
    private static final Logger log = LoggerFactory.getLogger(UpdateProductSaleQuantityListener.class);
    private final ProductService productService;

    public UpdateProductSaleQuantityListener(ProductService productService) {
        this.productService = productService;
    }

    @KafkaListener(
        topics = BaseKafkaConstants.Topic.UPDATE_PRODUCT_SALE_QUANTITY,
        groupId = BaseKafkaConstants.GroupId.UPDATE_PRODUCT_SALE_QUANTITY,
        concurrency = BaseKafkaConstants.Consumers.UPDATE_PRODUCT_SALE_QUANTITY
    )
    public void receiveMessage(
        @Payload String payload,
        @Header(name = KafkaHeaders.RECEIVED_KEY, required = false) String ignoredKey,
        @Header(KafkaHeaders.RECEIVED_PARTITION) int ignoredPartition,
        Acknowledgment ack
    ) {
        log.info("[HANDLE_UPDATE_PRODUCT_SALE_QUANTITY_REQUEST] - message payload: {}", payload);

        if (Objects.isNull(payload)) {
            log.error("[HANDLE_UPDATE_PRODUCT_SALE_QUANTITY_FAILED] - message payload is null");
            ack.acknowledge();
            return;
        }

        UpdateProductSaleQuantityEvent updateSaleQuantityEvent;

        try {
            updateSaleQuantityEvent = JsonUtils.parseJson(payload, UpdateProductSaleQuantityEvent.class);

            if (Objects.isNull(updateSaleQuantityEvent) || Objects.isNull(updateSaleQuantityEvent.getProductId())) {
                log.error("[HANDLE_UPDATE_PRODUCT_SALE_QUANTITY_FAILED] - Event content or productId null");
                ack.acknowledge();
                return;
            }

            productService.increaseProductSaleQuantity(updateSaleQuantityEvent);
        } catch (Exception e) {
            log.error("[HANDLE_UPDATE_PRODUCT_SALE_QUANTITY_ERROR] - Error. {}", e.getMessage(), e);
        }

        ack.acknowledge();
    }
}
