package dev.pk.demo.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);
    private ConsumerRecord<String, String> lastConsumerRecord;

    @KafkaListener(topics = "${application.kafka.topic.name}")
    public void consume(ConsumerRecord<String, String> consumerRecord) throws InterruptedException {
        lastConsumerRecord = consumerRecord;
        log.info("Consumed, Key: {}, Value: {}", consumerRecord.key(), consumerRecord.value());
    }

    public ConsumerRecord<String, String> getLastConsumerRecord() {
        return lastConsumerRecord;
    }
}