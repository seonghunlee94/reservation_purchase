package com.seonghun.module_order_service.domain.order.dto.response;

import com.seonghun.module_order_service.domain.order.domain.Orders;
import lombok.Builder;

@Builder
public record OrderResponseDto(

        Long id,

        String userId,

        String productId,

        String status

){
    public static OrderResponseDto responseDto(Orders order) {
        return OrderResponseDto.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .productId(order.getProductId())
                .build();
    }
}
