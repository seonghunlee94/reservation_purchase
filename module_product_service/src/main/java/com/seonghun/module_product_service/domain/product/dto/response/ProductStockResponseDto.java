package com.seonghun.module_product_service.domain.product.dto.response;

import com.seonghun.module_product_service.domain.product.domain.ProductStock;
import lombok.Builder;

@Builder
public record ProductStockResponseDto(

        Long id,
        String name,
        Long stock
){
    public static ProductStockResponseDto responseDto(ProductStock productStock) {
        return ProductStockResponseDto.builder()
                .id(productStock.getId())
                .name(productStock.getName())
                .stock(productStock.getStock())
                .build();
    }
}
