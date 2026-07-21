package com.example.orderservice.kafka;


import dto.OrderEventDTO;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class OrderPreducer {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(OrderEventDTO.class);

    private final NewTopic orderTopic;
    private final KafkaTemplate<String, OrderEventDTO> kafkaTemplate;

    public OrderPreducer(NewTopic orderTopic,
                         KafkaTemplate<String, OrderEventDTO> kafkaTemplate) {
        this.orderTopic = orderTopic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(OrderEventDTO orderEventDTO) {

        LOGGER.info("Sending order event {}", orderEventDTO);

        Message<OrderEventDTO> message = MessageBuilder
                .withPayload(orderEventDTO)
                .setHeader(KafkaHeaders.TOPIC, orderTopic.name())
                .build();

        kafkaTemplate.send(message);
    }
}