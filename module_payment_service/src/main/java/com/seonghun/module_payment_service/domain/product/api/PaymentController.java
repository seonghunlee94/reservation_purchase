package com.seonghun.module_payment_service.domain.product.api;

import com.seonghun.module_payment_service.domain.product.application.PaymentService;
import com.seonghun.module_payment_service.domain.product.application.RedisService;
import com.seonghun.module_payment_service.domain.product.domain.Orders;
import com.seonghun.module_payment_service.domain.product.dto.request.ProductOrderDto;
import com.seonghun.module_payment_service.domain.product.dto.response.PaymentResponseDto;
import com.seonghun.module_payment_service.global.client.ModuleProductClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

    private final PaymentService paymentService;

    private final ModuleProductClient moduleProductClient;
    private final RedisService redisService;


    @Autowired
    public PaymentController(PaymentService paymentService, ModuleProductClient moduleProductClient, RedisService redisService) {
        this.paymentService = paymentService;
        this.moduleProductClient = moduleProductClient;
        this.redisService = redisService;
    }

    /*
        결제 페이지 이동 전에 redis에 값 넣어주기
    */
    @PostMapping("/stock/save/{productName}")
    public ResponseEntity<Long> saveStock(@PathVariable String productName) {

        Long productStock = moduleProductClient.getProckStock(productName);

        redisService.setValues(productName, productStock);

        return ResponseEntity.ok().body(productStock);
    }


    /*
        결제 페이지 이동
    */
    @GetMapping("/buy/{productName}")
    public ResponseEntity<PaymentResponseDto> buyProduct(@PathVariable String productName) {

        Long productStock = moduleProductClient.getProckStock(productName);

        // 레디스 개수 감소로 변경하기
        PaymentResponseDto paymentInfo = paymentService.prePay(productName, productStock);

        return ResponseEntity.ok().body(paymentInfo);

    }


    /*
        결제
    */
    @PostMapping("/order")
    public ResponseEntity<Orders> orderProduct(@RequestHeader String name, @RequestBody ProductOrderDto order) {

        String productId = order.productId();
        Orders orderProduct = paymentService.orderProduct(name, productId);

        return ResponseEntity.ok().body(orderProduct);
    }

}
