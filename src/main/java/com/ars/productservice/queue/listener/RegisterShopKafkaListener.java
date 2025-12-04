package com.ars.productservice.queue.listener;

import com.ars.productservice.service.ShopService;
import com.dct.model.common.JsonUtils;
import com.dct.model.constants.BaseKafkaConstants;
import com.dct.model.event.UserCreatedEvent;

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
public class RegisterShopKafkaListener {
    private static final Logger log = LoggerFactory.getLogger(RegisterShopKafkaListener.class);
    private final ShopService shopService;

    public RegisterShopKafkaListener(ShopService shopService) {
        this.shopService = shopService;
    }

    @KafkaListener(
        topics = BaseKafkaConstants.Topic.USER_CREATED,
        groupId = BaseKafkaConstants.GroupId.USER_CREATED,
        concurrency = BaseKafkaConstants.Consumers.USER_CREATED
    )
    public void receiveMessage(
        @Payload String payload,
        @Header(name = KafkaHeaders.RECEIVED_KEY, required = false) String ignoredKey,
        @Header(KafkaHeaders.RECEIVED_PARTITION) int ignoredPartition,
        Acknowledgment ack
    ) {
        log.info("[HANDLE_REGISTER_SHOP_REQUEST] - message payload: {}", payload);

        if (Objects.isNull(payload)) {
            log.error("[HANDLE_REGISTER_SHOP_FAILED] - message payload is null");
            ack.acknowledge();
            return;
        }

        UserCreatedEvent userCreatedEvent = null;

        try {
            userCreatedEvent = JsonUtils.parseJson(payload, UserCreatedEvent.class);

            if (Objects.isNull(userCreatedEvent) || Objects.isNull(userCreatedEvent.getUserId())) {
                log.error("[HANDLE_REGISTER_SHOP_FAILED] - Register shop event content or userId is null");
                ack.acknowledge();
                return;
            }

            shopService.registerShop(userCreatedEvent);
        } catch (Exception e) {
            log.error("[HANDLE_REGISTER_SHOP_ERROR] - Register shop request error. {}", e.getMessage(), e);

            if (Objects.nonNull(userCreatedEvent)) {
                shopService.rollbackRegisterShop(userCreatedEvent, e.getMessage());
            }
        }

        ack.acknowledge();
    }
}
