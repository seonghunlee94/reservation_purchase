package com.seonghun.module_order_service.domain.order.api;

import com.seonghun.module_order_service.domain.order.application.OrderService;
import com.seonghun.module_order_service.domain.order.dto.request.OrderRequestDto;
import com.seonghun.module_order_service.domain.order.dto.response.OrderResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    /*
        주문 목록
    */
    @PostMapping("/create")
    public ResponseEntity<OrderResponseDto> orderProduct(@RequestBody OrderRequestDto order) {

        String productName = order.productName();
        String username = order.username();
        OrderResponseDto orderProduct = orderService.orderProduct(productName, username);

        return ResponseEntity.ok().body(orderProduct);
    }


}
