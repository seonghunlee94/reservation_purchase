package com.seonghun.module_payment_service.domain.product.api;

import com.seonghun.module_payment_service.domain.product.application.PaymentService;
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


    @Autowired
    public PaymentController(PaymentService paymentService, ModuleProductClient moduleProductClient) {
        this.paymentService = paymentService;
        this.moduleProductClient = moduleProductClient;
    }
    /*
        결제 페이지 이동
    */
    @GetMapping("/buy/{productName}")
    public ResponseEntity<PaymentResponseDto> buyProduct(@PathVariable String productName) {

        // 레디스에서 재고 가져오는 것으로 변경하기.
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
