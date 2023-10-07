package com.nvelaborja.producer.kafka;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KafkaMessage {
    @JsonIgnore
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private String message;

    private Date timestamp;

    public KafkaMessage(String message, Date timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public KafkaMessage() {
    }

    @JsonIgnore
    public String toJson() throws JsonProcessingException {
        return objectMapper.writeValueAsString(this);
    }

    @JsonIgnore
    public static KafkaMessage fromJson(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, KafkaMessage.class);
    }

    public String getMessage() {
        return message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
