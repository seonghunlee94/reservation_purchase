package com.seonghun.module_product_service.domain.product.api;

import com.seonghun.module_product_service.domain.product.application.ProductService;
import com.seonghun.module_product_service.domain.product.domain.ProductStock;
import com.seonghun.module_product_service.domain.product.domain.Products;
import com.seonghun.module_product_service.domain.product.dto.request.ProductCreateRequestDto;
import com.seonghun.module_product_service.domain.product.dto.response.ProductResponseDto;
import com.seonghun.module_product_service.domain.product.dto.response.ProductStockResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vi/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /*
        상품 목록 가져오기.
    */
    @GetMapping("/list")
    public ResponseEntity<List<Products>> getProduct() {
        List<Products> productsList = productService.getProductList();
        return ResponseEntity.ok(productsList);
    }

    /*
        상품 디테일 정보 가져오기
    */
    @GetMapping("/{id}/detail")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable String id) {
        try {
            Long productId = Long.valueOf(id);

            Products getProducts = productService.getProductDetailById(productId);
            ProductResponseDto responseDto = ProductResponseDto.responseDto(getProducts);
            return ResponseEntity.ok().body(responseDto);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }

    }

    /*
        남은 수량 가져오기.
    */
    @GetMapping("/{name}/stock")
    public ResponseEntity<ProductStockResponseDto> getProductStockByName(@PathVariable String name) {

        try {

            ProductStock getProductStock = productService.getProductStockByName(name);
            ProductStockResponseDto responseDto = ProductStockResponseDto.responseDto(getProductStock);
            return ResponseEntity.ok().body(responseDto);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }

    }


    /*
        상품 등록
    */
    @PostMapping("/create")
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody ProductCreateRequestDto requestDto) {
        Products createdProducts = productService.createProduct(requestDto.name(), requestDto.content(), requestDto.price(), requestDto.stock());
        ProductResponseDto responseDto = ProductResponseDto.responseDto(createdProducts);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

}
