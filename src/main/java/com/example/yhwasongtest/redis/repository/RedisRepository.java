package com.example.yhwasongtest.redis.repository;

import com.example.yhwasongtest.redis.model.RedisModel;
import org.springframework.data.repository.CrudRepository;

public interface RedisRepository extends CrudRepository<RedisModel, Long> {
}
