package com.seonghun.module_product_service.domain.product.application;

import com.seonghun.module_product_service.domain.product.dao.ProductRepository;
import com.seonghun.module_product_service.domain.product.dao.ProductStockRepository;
import com.seonghun.module_product_service.domain.product.domain.ProductStock;
import com.seonghun.module_product_service.domain.product.domain.Products;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductStockRepository productStockRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductStockRepository productStockRepository) {
        this.productRepository = productRepository;
        this.productStockRepository = productStockRepository;
    }

    /*
        상품 목록 가져오기.
    */
    public List<Products> getProductList() {
        return productRepository.findProductsBy();
    }

    /*
        상품 디테일 정보 가져오기
    */
    public Products getProductDetailById(Long id) {
        Products product = productRepository.findProductById(id);

        if (product == null) {
            throw new IllegalArgumentException();
        }


        return product;
    }

    /*
        남은 수량 가져오기.
    */
    public ProductStock getProductStockByName(String name) {

        ProductStock productStock = productStockRepository.findProductStockByName(name);

        if (productStock == null) {
            throw new IllegalArgumentException();
        }

        return productStock;

    }

    /*
        상품 등록
    */
    public Products createProduct(String name, String content, Long price, Long stock) {

        Products newProducts = new Products();
        newProducts.setName(name);
        newProducts.setContent(content);
        newProducts.setPrice(price);

        productRepository.save(newProducts);

        ProductStock productStock = new ProductStock();
        productStock.setName(name);
        productStock.setStock(stock);

        productStockRepository.save(productStock);

        return newProducts;
    }

}
