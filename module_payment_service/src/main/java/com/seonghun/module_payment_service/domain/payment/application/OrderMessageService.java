package com.seonghun.module_payment_service.domain.payment.application;

import com.seonghun.module_payment_service.domain.payment.dto.request.OrderMessage;
import com.seonghun.module_payment_service.global.common.Producer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderMessageService {

    private final Producer producer;

    private static final String EXCHANGE = "order.exchange";
    private static final String ROUTE_KEY = "order.key";

    public void sendOrder(String productName, String userId) {
        var message = OrderMessage.builder()
                .productName(productName)
                .userId(userId)
                .build();
        producer.producer(EXCHANGE, ROUTE_KEY, message);
    }

}
