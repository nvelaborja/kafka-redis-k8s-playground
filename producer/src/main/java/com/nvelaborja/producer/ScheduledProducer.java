package com.nvelaborja.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nvelaborja.producer.kafka.KafkaConfig;
import com.nvelaborja.producer.kafka.KafkaMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduledProducer {
    private static final Logger log = LoggerFactory.getLogger(ScheduledProducer.class);

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(fixedRate = 10000)
    public void sendMessage() throws JsonProcessingException {
        log.info(String.format("Test message from producer %s: %s", ProducerApplication.producerId, new Date()));

        KafkaMessage newMessage = new KafkaMessage(String.format("Test message from producer %s", ProducerApplication.producerId), new Date());

        kafkaTemplate.send(KafkaConfig.TEST_TOPIC, newMessage.toJson());
    }
}
