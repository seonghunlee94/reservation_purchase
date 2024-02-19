package com.seonghun.module_product_service.domain.product.application;


import com.seonghun.module_product_service.domain.product.dao.OrderRepository;
import com.seonghun.module_product_service.domain.product.dao.ProductStockRepository;
import com.seonghun.module_product_service.domain.product.domain.Orders;
import com.seonghun.module_product_service.domain.product.domain.ProductStock;
import com.seonghun.module_product_service.domain.product.dto.response.PaymentResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class PaymentService {

    private final ProductStockRepository productStockRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public PaymentService(ProductStockRepository productStockRepository, OrderRepository orderRepository) {
        this.productStockRepository = productStockRepository;
        this.orderRepository = orderRepository;
    }

    /*
        결제 페이지 이동 시 수량 감소.
    */
//    public PaymentResponseDto prePay(String name, ProductStock productStock) {
    public PaymentResponseDto prePay(ProductStock productStock) {

        productStock.setStock(productStock.getStock() - 1);

        return PaymentResponseDto.builder()
                .id(productStock.getId())
//                .userId(name)
                .productId(productStock.getName())
                .stock(productStock.getStock())
                .build();

    }

    /*
        결제 시 Order 테이블에 반영
    */
    public Orders orderProduct(String id, String productId) {

        Orders order = new Orders();
        order.setUserId(id);
        order.setProductId(productId);

        return orderRepository.save(order);
    }



}
