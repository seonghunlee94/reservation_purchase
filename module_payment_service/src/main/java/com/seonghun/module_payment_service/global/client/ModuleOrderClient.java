package com.seonghun.module_payment_service.global.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "module-order-service", url = "localhost:8093/api/v1/internal/orders/feign")
public interface ModuleOrderClient {


    @PostMapping("/create")
    void orderProduct(@RequestHeader String productId, @RequestHeader String username);

}
