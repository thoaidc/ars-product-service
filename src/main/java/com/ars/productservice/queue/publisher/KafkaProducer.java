package com.ars.productservice.queue.publisher;

import com.dct.model.config.properties.KafkaProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnClass(KafkaTemplate.class)
public class KafkaProducer {
    private final Logger log = LoggerFactory.getLogger(KafkaProducer.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaProperties kafkaProperties;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate, KafkaProperties kafkaProperties) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaProperties = kafkaProperties;
    }

    public void sendMessageRegisterShopSuccessful(String event) {
        log.info("[SEND_CREATE_USER_SHOP_COMPLETION_TOPIC] - {}", event);
        kafkaTemplate.send(kafkaProperties.getTopics().getUserRegisterShopCompletion(), event);
    }

    public void sendMessageRegisterShopFailure(String event) {
        log.error("[SEND_CREATE_USER_SHOP_FAILURE_TOPIC] - {}", event);
        kafkaTemplate.send(kafkaProperties.getTopics().getUserRegisterShopFailure(), event);
    }
}
