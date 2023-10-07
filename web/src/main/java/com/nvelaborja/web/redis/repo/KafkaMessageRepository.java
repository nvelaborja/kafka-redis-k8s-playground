package com.nvelaborja.web.redis.repo;

import com.nvelaborja.web.redis.entity.KafkaMessageEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KafkaMessageRepository extends CrudRepository<KafkaMessageEntity, String> {}