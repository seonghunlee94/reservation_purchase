package com.seonghun.module_product_service.domain.product.dto.response;

import com.seonghun.module_product_service.domain.product.domain.Products;
import lombok.Builder;

@Builder
public record ProductResponseDto (

        Long id,
        String name,
        String content,
        Long price
){
    public static ProductResponseDto responseDto(Products products) {
        return ProductResponseDto.builder()
                .id(products.getId())
                .name(products.getName())
                .content(products.getContent())
                .price(products.getPrice())
                .build();
    }
}
