package com.seonghun.module_payment_service.domain.product.application;


import com.seonghun.module_payment_service.domain.product.dao.OrderRepository;
import com.seonghun.module_payment_service.domain.product.domain.Orders;
import com.seonghun.module_payment_service.domain.product.dto.response.PaymentResponseDto;
import com.seonghun.module_payment_service.global.client.ModuleProductClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class PaymentService {

    private final OrderRepository orderRepository;
    private final RedisService redisService;
    private final ModuleProductClient moduleProductClient;


    @Autowired
    public PaymentService(OrderRepository orderRepository, RedisService redisService, ModuleProductClient moduleProductClient) {
        this.orderRepository = orderRepository;
        this.redisService = redisService;
        this.moduleProductClient = moduleProductClient;
    }

    /*
        결제 페이지 이동 시
    */
    public PaymentResponseDto payProduct(String productName) {

        // 레디스 재고 감소
        Long decreaseStock = redisService.decreaseStock(productName);

        // DB에 redis 값 매핑
        moduleProductClient.updateProductStock(productName, decreaseStock);

        return PaymentResponseDto.builder()
                .productId(productName)
                .stock(decreaseStock)
                .build();

    }

    /*
        결제 시 Order 테이블에 반영
    */
    public Orders orderProduct(String id, String productId) {

        Orders order = new Orders();
        order.setUserId(id);
        order.setProductId(productId);

        return orderRepository.save(order);
    }



}
