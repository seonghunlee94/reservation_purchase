package com.seonghun.module_product_service.domain.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record ProductCreateRequestDto (

        Long id,

        @NotBlank
        String name,

        @NotBlank
        String content,

        Long price,

        Long stock
) {

}
