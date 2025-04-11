package dev.pk.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class KafkaProducer {

    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);
    private static final String topicName = "quickstart-events";

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final AtomicInteger i = new AtomicInteger(0);

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(timeUnit = TimeUnit.SECONDS, fixedDelay = 1L)
    void send() {
        long count = i.getAndIncrement();
        String key = "key" + i;
        String value = "value" + i;
        kafkaTemplate.send(topicName, key, value);
        log.info("Produced Message to Topic: {} with key: {}, value: {}", topicName, key, value);
    }

}