package dev.pk.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    private static final String topicName = "quickstart-events";

    @KafkaListener(topics = {topicName})
    public void processMessage(String message) {
        log.info("Consumed: {}", message);
    }
}
