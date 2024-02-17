package com.seonghun.module_product_service.domain.product.dao;

import com.seonghun.module_product_service.domain.product.domain.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {
    Products findProductById(Long id);
    List<Products> findProductsBy();
}
