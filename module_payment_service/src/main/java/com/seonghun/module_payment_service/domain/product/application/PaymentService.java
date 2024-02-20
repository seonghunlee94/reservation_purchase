package com.seonghun.module_payment_service.domain.product.application;


import com.seonghun.module_payment_service.domain.product.dao.OrderRepository;
import com.seonghun.module_payment_service.domain.product.domain.Orders;
import com.seonghun.module_payment_service.domain.product.dto.response.PaymentResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class PaymentService {

    private final OrderRepository orderRepository;

    @Autowired
    public PaymentService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /*
        결제 페이지 이동 시
    */
    public PaymentResponseDto prePay(String productName,Long stock) {

        return PaymentResponseDto.builder()
                .id(null) // id 필요시 controller에서 받아오는 방식 고민해보기.
//                .userId(name)
                .productId(productName)
                .stock(stock)
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