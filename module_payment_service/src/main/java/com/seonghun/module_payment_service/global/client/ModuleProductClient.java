package com.seonghun.module_payment_service.global.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "module-product-service", url = "localhost:8091/api/v1/internal/products/feign")
public interface ModuleProductClient {

    @GetMapping("/stock")
    Long getProduckStock(@RequestParam String name);


    @PostMapping("/save/stock")
    void updateProductStock(@RequestHeader String productName, @RequestHeader Long stock);
}
