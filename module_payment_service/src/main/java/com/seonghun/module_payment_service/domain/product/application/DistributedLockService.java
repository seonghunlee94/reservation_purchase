package com.seonghun.module_payment_service.domain.product.application;

import com.seonghun.module_payment_service.domain.product.dto.response.PaymentResponseDto;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class DistributedLockService {

    private PaymentService paymentService;

    private RedissonClient redissonClient;

    @Autowired
    public DistributedLockService(PaymentService paymentService, RedissonClient redissonClient) {
        this.paymentService = paymentService;
        this.redissonClient = redissonClient;
        // 분산 락 설정
        lock = redissonClient.getLock("distributed-lock");
    }


    private RLock lock;

    public DistributedLockService() {

    }

    public void acquireLock() {
        // 분산 락 획득
        lock.lock();
    }

    public void releaseLock() {
        // 분산 락 해제
        lock.unlock();
    }

    public boolean tryAcquireLock(long waitTime, long leaseTime, TimeUnit timeUnit) throws InterruptedException {
        // 일정 시간 동안 대기 후 분산 락 획득 시도
        return lock.tryLock(waitTime, leaseTime, timeUnit);
    }

    // 사용 예시
    public PaymentResponseDto payProductDistributedLock(String productName) throws InterruptedException {
        // 분산 락 획득
        acquireLock();
        PaymentResponseDto payment;
        try {
            // Lock 걸어서 제품 개수 감소
            payment = paymentService.payProduct(productName);

        } finally {
            // 분산 락 해제
            releaseLock();
        }
        return payment;
    }
}
