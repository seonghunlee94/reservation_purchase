package com.seonghun.module_payment_service.domain.payment.dto.request;

import lombok.Builder;

@Builder
public record ProductOrderDto(

        Long id,

        String username,
        String productId

) {

}
