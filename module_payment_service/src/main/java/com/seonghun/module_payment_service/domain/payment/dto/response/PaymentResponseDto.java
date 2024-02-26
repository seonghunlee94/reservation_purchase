package com.seonghun.module_payment_service.domain.payment.dto.response;

import lombok.Builder;

@Builder
public record PaymentResponseDto(

        Long id,

        String userId,

        String productId,

        Long stock

){

}
