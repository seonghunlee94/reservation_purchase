package com.seonghun.module_payment_service.domain.product.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    // 키-벨류 설정
    public void setValues(String productName, Long stock){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(productName, String.valueOf(stock));
    }

    // 키값으로 벨류 가져오기
    public String getValues(String productName){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.get(productName);
    }

    // 키-벨류 삭제
    public void delValues(String username) {
        redisTemplate.delete(username);
    }
}
