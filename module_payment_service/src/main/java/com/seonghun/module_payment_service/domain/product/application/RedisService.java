package com.seonghun.module_payment_service.domain.product.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

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


    // 재고 증가
    public Long increaseStock(String productName) {
        long currnetStock = Long.parseLong(getValues(productName));

        return null;
    }


    // 재고 감소
    public Long decreaseStock(String productName) {
        Long stock = redisTemplate.opsForValue().decrement(productName);

        if (stock < 0) {
            redisTemplate.opsForValue().set(productName, "0");
            throw new IllegalArgumentException("재고가 부족합니다.");
        }

        return stock;

    }



}
