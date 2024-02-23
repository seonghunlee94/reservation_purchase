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
        // 재고가 없는 경우 예외 처리 해주기.

        return ResponseEntity.ok().body(productStock);

    }

    /*
        결제 -
    */
    @PostMapping("/buy/{productName}")
    public ResponseEntity<PaymentResponseDto> buyProduct(@PathVariable String productName) {

        Long productStock = moduleProductClient.getProduckStock(productName);

        // 레디스 개수 감소로 변경하기
        if (productStock > 0) {
            PaymentResponseDto paymentInfo = paymentService.payProduct(productName);
            // user Id @RequestHeader로 받아서 넣어주기.
            // paymentInfo.userId();
            return ResponseEntity.ok().body(paymentInfo);
        } else if(productStock == 0) {

        }

        return ResponseEntity.ok().body(null);

    }


    /*
        주문 목록
    */
    @PostMapping("/order")
    public ResponseEntity<Orders> orderProduct(@RequestHeader String name, @RequestBody ProductOrderDto order) {

        String productId = order.productId();
        Orders orderProduct = paymentService.orderProduct(name, productId);

        return ResponseEntity.ok().body(orderProduct);
    }

}
