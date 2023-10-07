package com.nvelaborja.producer;

import com.nvelaborja.producer.kafka.KafkaConfig;
import com.nvelaborja.producer.kafka.KafkaMessage;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Date;
import java.util.UUID;

@SpringBootApplication
@EnableScheduling
public class ProducerApplication {
    private static final Logger log = LoggerFactory.getLogger(ProducerApplication.class);
    protected static final String producerId = UUID.randomUUID().toString();

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name(KafkaConfig.TEST_TOPIC)
                .partitions(10)
                .replicas(1)
                .build();
    }

    @Bean
    public ApplicationRunner runner(KafkaTemplate<String, String> template) {
        log.info(String.format("Producer %s starting: %s", producerId, new Date()));

        KafkaMessage newMessage = new KafkaMessage(String.format("Producer %s starting.", producerId), new Date());

        return args -> template.send(KafkaConfig.TEST_TOPIC, newMessage.toJson());
    }
}