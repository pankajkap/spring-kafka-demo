package dev.pk.demo.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.util.concurrent.TimeUnit;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(
        kraft = true,
        brokerProperties = {
                "security.protocol=PLAINTEXT",
                "listener=PLAINTEXT://localhost:9099",
                "port=9099",
                "auto.create.topics.enable=true"
        },
        topics = {
                "${application.kafka.topic.name}"
        },
        partitions = 1
)
class ProducerConsumerWithEmbeddedKafkaTest {

    @Autowired
    EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    KafkaConsumer consumer;

    @Autowired
    KafkaProducer producer;

    @Value("${application.kafka.topic.name}")
    String topicName;

    @Test
    void testProducerConsumer() throws InterruptedException {
        String key = "key1";
        String value = "value1";

        producer.send(topicName, key, value);

        TimeUnit.SECONDS.sleep(1);
        ConsumerRecord<String, String> consumerRecord = consumer.getLastConsumerRecord();
        Assertions.assertEquals(key, consumerRecord.key());
        Assertions.assertEquals(value, consumerRecord.value());
    }
}
