package com.seonghun.module_order_service.domain.order.api;

import com.seonghun.module_order_service.domain.order.application.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/internal/orders")
public class InternalModuleOrderController {

    private final OrderService orderService;

    @Autowired
    public InternalModuleOrderController(OrderService orderService) {
        this.orderService = orderService;
    }



    @PostMapping("/feign/create")
    public void orderProduct(@RequestHeader String productId, @RequestHeader String username) {

        orderService.orderProduct(productId, username);

    }


}
