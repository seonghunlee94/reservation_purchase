package com.seonghun.module_payment_service.domain.payment.application;

import com.seonghun.module_payment_service.domain.payment.dto.response.PaymentResponseDto;
import com.seonghun.module_payment_service.global.client.ModuleProductClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class PaymentService {

    private final RedisService redisService;
    private final ModuleProductClient moduleProductClient;

    private final OrderMessageService orderMessageService;

    @Autowired
    public PaymentService(RedisService redisService, ModuleProductClient moduleProductClient, OrderMessageService orderMessageService) {
        this.redisService = redisService;
        this.moduleProductClient = moduleProductClient;
        this.orderMessageService = orderMessageService;
    }

    /*
        결제 페이지 이동 시
    */
    public PaymentResponseDto payProduct(String productName, String userId) {

        // 레디스 재고 감소
        Long decreaseStock = redisService.decreaseStock(productName);

        // DB에 redis 값 매핑
        moduleProductClient.updateProductStock(productName, decreaseStock);

        // rabbitMQ 데이터 큐 보내기.
        orderMessageService.sendOrder(productName, userId);


        return PaymentResponseDto.builder()
                .productId(productName)
                .stock(decreaseStock)
                .build();

    }


}
