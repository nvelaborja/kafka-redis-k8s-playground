package com.nvelaborja.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nvelaborja.web.kafka.config.KafkaConfig;
import com.nvelaborja.web.redis.entity.KafkaMessageEntity;
import com.nvelaborja.web.redis.repo.KafkaMessageRepository;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
@EnableCaching
public class ListenerApplication {
    private static final Logger log = LoggerFactory.getLogger(ListenerApplication.class);
    private final KafkaMessageRepository kafkaMessageRepository;

    @Autowired
    public ListenerApplication(KafkaMessageRepository kafkaMessageRepository) {
        this.kafkaMessageRepository = kafkaMessageRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(ListenerApplication.class, args);
    }

    @GetMapping("/messages")
    public String getMessages(@RequestParam(value = "prefix", defaultValue = "") String prefix) {
        List<KafkaMessageEntity> messages = new ArrayList<>();

        if (StringUtils.isBlank(prefix)) {
            kafkaMessageRepository.findAll().iterator().forEachRemaining(messages::add);
        } else {
            kafkaMessageRepository.findAll().iterator().forEachRemaining(message -> {
                if (message.getMessage().startsWith(prefix)) {
                    messages.add(message);
                }
            });
        }

        return messages.stream().map(kafkaMessageEntity -> {
            try {
                return kafkaMessageEntity.toJson();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }).toList().toString();
    }

    @KafkaListener(id = "ListenerApplication", topics = KafkaConfig.TEST_TOPIC)
    public void listen(String in) {
        log.info(String.format("Received kafka message: %s", in));

        try {
            kafkaMessageRepository.save(KafkaMessageEntity.fromJson(in));
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize kafka message", e);
            throw new RuntimeException(e);
        }
    }
}