package com.seonghun.module_product_service.domain.product.api;

import com.seonghun.module_product_service.domain.product.application.PaymentService;
import com.seonghun.module_product_service.domain.product.application.ProductService;
import com.seonghun.module_product_service.domain.product.domain.Orders;
import com.seonghun.module_product_service.domain.product.domain.ProductStock;
import com.seonghun.module_product_service.domain.product.dto.request.ProductOrderDto;
import com.seonghun.module_product_service.domain.product.dto.response.PaymentResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vi/payment")
public class PaymentController {

    private final PaymentService paymentService;
    private final ProductService productService;

    @Autowired
    public PaymentController(PaymentService paymentService, ProductService productService) {
        this.paymentService = paymentService;
        this.productService = productService;
    }
    /*
        결제 페이지 이동
    */
    @GetMapping("/buy/{productName}")
//    public ResponseEntity<PaymentResponseDto> buyProduct(@PathVariable String productName, @RequestHeader String username) {
    public ResponseEntity<PaymentResponseDto> buyProduct(@PathVariable String productName) {
        ProductStock productStock = productService.getProductStockByName(productName);

        // 개수 감소
//        PaymentResponseDto paymentInfo = paymentService.prePay(username, productStock);
        PaymentResponseDto paymentInfo = paymentService.prePay(productStock);

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
