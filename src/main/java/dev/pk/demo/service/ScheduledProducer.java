package dev.pk.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@ConditionalOnExpression("${application.kafka.scheduled-producer.enabled:false}")
public class ScheduledProducer {

    private final AtomicInteger i = new AtomicInteger(0);
    private final String topicName;
    private final KafkaProducer kafkaProducer;

    public ScheduledProducer(@Value("${application.kafka.topic.name}") String topicName, KafkaProducer kafkaProducer) {
        this.topicName = topicName;
        this.kafkaProducer = kafkaProducer;
    }

    @Scheduled(timeUnit = TimeUnit.SECONDS, fixedDelay = 1L)
    void send() {
        String key = "key" + i.getAndIncrement();
        String value = "value" + i;
        kafkaProducer.send(topicName, key, value);
    }
}
