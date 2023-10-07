package com.nvelaborja.web.redis.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Date;

@RedisHash("KafkaMessage")
@JsonIgnoreProperties(ignoreUnknown = true)
public class KafkaMessageEntity implements Serializable {
    @JsonIgnore
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Id
    private String id;
    private String message;
    private Date timestamp;

    public KafkaMessageEntity(String id, String message, Date timestamp) {
        this.id = id;
        this.message = message;
        this.timestamp = timestamp;
    }

    public KafkaMessageEntity() {
    }

    @JsonIgnore
    public String toJson() throws JsonProcessingException {
        return objectMapper.writeValueAsString(this);
    }

    @JsonIgnore
    public static KafkaMessageEntity fromJson(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, KafkaMessageEntity.class);
    }

    public String getMessage() {
        return message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getId() {
        return id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setId(String id) {
        this.id = id;
    }
}
