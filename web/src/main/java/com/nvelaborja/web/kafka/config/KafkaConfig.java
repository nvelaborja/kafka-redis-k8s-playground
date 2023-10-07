package com.nvelaborja.web.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    public static final String TEST_TOPIC = "test";

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name(KafkaConfig.TEST_TOPIC)
                .partitions(10)
                .replicas(1)
                .build();
    }
}
