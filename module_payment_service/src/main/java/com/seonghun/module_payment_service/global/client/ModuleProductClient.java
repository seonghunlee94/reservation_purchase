package com.seonghun.module_payment_service.global.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "module-product-service", url = "localhost:8091/api/v1/internal/products/feign")
public interface ModuleProductClient {

    @GetMapping("/stock")
    Long getProckStock(@RequestParam String name);

}
