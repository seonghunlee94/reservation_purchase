package com.seonghun.module_order_service.global.common;

import com.seonghun.module_order_service.domain.order.application.OrderService;
import com.seonghun.module_order_service.domain.order.dto.request.OrderMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderConsumer {

    private final OrderService orderService;



    @RabbitListener(queues = "order.queue")
    public void userOrderConsumer(
        OrderMessage orderMessage
    ) {
        orderService.orderProduct(orderMessage.getProductName(), orderMessage.getUserId());
//        log.info("message queue >> {}", orderMessage);
    }
}
