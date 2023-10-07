package com.nvelaborja.producer.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class KafkaMessageTest {

    @Test
    public void testKafkaMessageToJson() throws JsonProcessingException {
        Date now = new Date();

        KafkaMessage kafkaMessage = new KafkaMessage("Unit testing is fun!", now);

        String json = kafkaMessage.toJson();

        String expectedJson = String.format("{\"message\":\"Unit testing is fun!\",\"timestamp\":%d}", now.getTime());

        Assertions.assertEquals(expectedJson, json);
    }

    @Test
    public void testKafkaMessageFromJson() throws JsonProcessingException {
        String message = "Unit testing is fun!";
        long time = 1696710365291L;

        String json = String.format("{\"message\":\"%s\",\"timestamp\":%d}", message, time);

        KafkaMessage kafkaMessage = KafkaMessage.fromJson(json);

        Assertions.assertNotNull(kafkaMessage);
        Assertions.assertEquals(message, kafkaMessage.getMessage());
        Assertions.assertEquals(time, kafkaMessage.getTimestamp().getTime());
    }
}
