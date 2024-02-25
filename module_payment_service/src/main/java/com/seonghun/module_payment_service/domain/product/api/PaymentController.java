package com.seonghun.module_payment_service.domain.product.api;

import com.seonghun.module_payment_service.domain.product.application.DistributedLockService;
import com.seonghun.module_payment_service.domain.product.application.RedisService;
import com.seonghun.module_payment_service.domain.product.dto.response.PaymentResponseDto;
import com.seonghun.module_payment_service.global.client.ModuleOrderClient;
import com.seonghun.module_payment_service.global.client.ModuleProductClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

    private final ModuleProductClient moduleProductClient;
    private final ModuleOrderClient moduleOrderClient;
    private final RedisService redisService;
    private final DistributedLockService distributedLockService;


    @Autowired
    public PaymentController(ModuleProductClient moduleProductClient, ModuleOrderClient moduleOrderClient, RedisService redisService, DistributedLockService distributedLockService) {
        this.moduleProductClient = moduleProductClient;
        this.moduleOrderClient = moduleOrderClient;
        this.redisService = redisService;
        this.distributedLockService = distributedLockService;
    }

    /*
        오픈 시간 전, redis에 재고 값 넣어주기
    */
    @PostMapping("/stock/save/{productName}")
    public ResponseEntity<Long> saveStock(@PathVariable String productName) {

        Long productStock = moduleProductClient.getProduckStock(productName);

        redisService.setValues(productName, productStock);

        return ResponseEntity.ok().body(productStock);
    }


    /*
        결제 페이지 이동 - 재고
    */
    @GetMapping("/check/{productName}")
    public ResponseEntity<Long> getProductStock(@PathVariable String productName) {

        Long productStock = moduleProductClient.getProduckStock(productName);

        return ResponseEntity.ok().body(productStock);

    }

    /*
        결제 -
    */
    @PostMapping("/buy/{productName}")
    public ResponseEntity<PaymentResponseDto> buyProduct(@PathVariable String productName, @RequestHeader String userId) throws InterruptedException {

        Long productStock = moduleProductClient.getProduckStock(productName);

        // 레디스 개수 감소로 변경하기
        if (productStock > 0) {

            PaymentResponseDto paymentInfo = distributedLockService.payProductDistributedLock(productName);
            moduleOrderClient.orderProduct(productName, userId);

            return ResponseEntity.ok().body(paymentInfo);
        }

        return ResponseEntity.ok().body(null);

    }

}
