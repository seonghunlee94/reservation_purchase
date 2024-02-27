package com.seonghun.module_order_service.domain.order.application;


import com.seonghun.module_order_service.domain.order.dao.OrderRepository;
import com.seonghun.module_order_service.domain.order.domain.Orders;
import com.seonghun.module_order_service.domain.order.dto.response.OrderResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /*
        결제 시 Order 테이블에 반영
    */
    public OrderResponseDto orderProduct(String productName, String username) {

        Orders order = new Orders();
        order.setUserId(username);
        order.setProductName(productName);

        order = orderRepository.save(order);

        return OrderResponseDto.builder()
                .id(order.getId())
                .userId(username)
                .productName(productName)
                .build();
    }



}
