package com.seonghun.module_order_service.domain.product.dto.request;

import lombok.Builder;

@Builder
public record OrderRequestDto(

        Long id,

        String username,
        String productId

) {

}
