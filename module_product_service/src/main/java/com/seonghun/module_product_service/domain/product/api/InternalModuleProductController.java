package com.seonghun.module_product_service.domain.product.api;

import com.seonghun.module_product_service.domain.product.application.ProductService;
import com.seonghun.module_product_service.domain.product.domain.ProductStock;
import com.seonghun.module_product_service.domain.product.dto.response.ProductResponseDto;
import com.seonghun.module_product_service.domain.product.dto.response.ProductStockResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/internal/products")
public class InternalModuleProductController {

    private final ProductService productService;

    @Autowired
    public InternalModuleProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/feign/stock")
    public ResponseEntity<Long> getProductStockByName(@RequestParam String name) {

        ProductStock getProductStock = productService.getProductStockByName(name);
        Long stock = getProductStock.getStock();
        return ResponseEntity.ok().body(stock);

    }

    @PostMapping("/feign/save/stock")
    public ResponseEntity<Void> updateProductStock(@RequestHeader String productName, @RequestHeader Long stock) {

        productService.updateProductStock(productName, stock);

        return ResponseEntity.ok().body(null);
    }

}
