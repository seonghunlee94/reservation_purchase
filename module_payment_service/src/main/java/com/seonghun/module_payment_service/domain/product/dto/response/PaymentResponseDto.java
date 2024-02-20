package com.seonghun.module_payment_service.domain.product.dto.response;

import com.seonghun.module_payment_service.domain.product.domain.Orders;
import lombok.Builder;

@Builder
public record PaymentResponseDto(

        Long id,

        String userId,

        String productId,

        Long stock

){
    public static PaymentResponseDto responseDto(Orders order) {
        return PaymentResponseDto.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .productId(order.getProductId())
                .build();
    }
}
