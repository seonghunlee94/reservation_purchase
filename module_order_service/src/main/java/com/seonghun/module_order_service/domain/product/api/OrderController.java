package com.seonghun.module_order_service.domain.product.api;

import com.seonghun.module_order_service.domain.product.application.OrderService;
import com.seonghun.module_order_service.domain.product.dto.request.OrderRequestDto;
import com.seonghun.module_order_service.domain.product.dto.response.OrderResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService paymentService;

    @Autowired
    public OrderController(OrderService paymentService) {
        this.paymentService = paymentService;
    }


    /*
        주문 목록
    */
    @PostMapping("/create")
    public ResponseEntity<OrderResponseDto> orderProduct(@RequestBody OrderRequestDto order) {

        String productId = order.productId();
        String username = order.username();
        OrderResponseDto orderProduct = paymentService.orderProduct(username, productId);

        return ResponseEntity.ok().body(orderProduct);
    }


}
