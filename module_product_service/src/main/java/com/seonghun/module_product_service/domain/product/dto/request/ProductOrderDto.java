package com.seonghun.module_product_service.domain.product.dto.request;

import lombok.Builder;

@Builder
public record ProductOrderDto(

        Long id,

        String username,
        String productId

) {

}
